/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp.actions.handlers;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.features.undoredo.RedoAction;

public class EMSRedoActionHandler extends EMSBasicActionHandler<RedoAction, EMSModelState, EMSModelServerAccess> {

   private static final Logger LOGGER = Logger.getLogger(EMSRedoActionHandler.class.getSimpleName());

   @Override
   public List<Action> executeAction(final RedoAction action, final EMSModelState modelState,
      final EMSModelServerAccess modelServerAccess) {

      CompletableFuture<Void> result = modelServerAccess.redo().thenAccept(response -> {
         int status = response.getStatusCode();
         switch (status) {
            case 200: // OK
            case 202: // Not redoable
               return;
            default:
               // Typically error 500: ModelServer error
               LOGGER.error("Invalid redo response: " + response.getStatusCode());
               return;
         }
      });

      // Make sure we wait for the redo result before proceeding with more actions
      // Don't block forever, though.
      // Note: this is especially important as the moment, because the ModelServer itself
      // isn't thread safe. If we send multiple parallel redo requests, it may try to handle
      // more than one at the same time, causing crashes. But even with a thread-safe model server,
      // it's a good idea to wait a bit to make sure we're in a consistent state before handling
      // more actions.
      int maxTries = 5;
      for (int i = 0; i < maxTries; i++) {
         try {
            result.get(1, TimeUnit.SECONDS);
         } catch (TimeoutException e) {
            LOGGER.warn("Redo action didn't complete in " + (i + 1) + "s");
         } catch (InterruptedException e) {
            LOGGER.error("Interrupted", e);
            Thread.currentThread().interrupt();
            break;
         } catch (ExecutionException e) {
            LOGGER.error("Failed to redo", e);
            break;
         }
      }

      return none();
   }

}
