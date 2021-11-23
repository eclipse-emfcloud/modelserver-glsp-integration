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
import org.eclipse.glsp.server.features.undoredo.UndoAction;

public class EMSUndoActionHandler
   extends EMSBasicActionHandler<UndoAction, EMSModelState, EMSModelServerAccess> {

   private static final Logger LOGGER = Logger.getLogger(EMSUndoActionHandler.class.getSimpleName());

   @Override
   public List<Action> executeAction(final UndoAction action, final EMSModelState modelState,
      final EMSModelServerAccess modelServerAccess) {

      CompletableFuture<Void> result = modelServerAccess.undo().thenAccept(response -> {
         int status = response.getStatusCode();
         switch (status) {
            case 200: // OK
            case 202: // Not undoable
               return;
            default:
               // Typically error 500: ModelServer error
               LOGGER.error("Invalid undo response: " + response.getStatusCode());
               return;
         }
      });
      // Make sure we wait for the undo result before proceeding with more actions
      // Don't block forever, though.
      // Note: this is especially important as the moment, because the ModelServer itself
      // isn't thread safe. If we send multiple parallel undo requests, it may try to handle
      // more than one at the same time, causing crashes. But even with a thread-safe model server,
      // it's a good idea to wait a bit to make sure we're in a consistent state before handling
      // more actions.
      int maxTries = 5;
      for (int i = 0; i < maxTries; i++) {
         try {
            result.get(1, TimeUnit.SECONDS);
         } catch (TimeoutException e) {
            LOGGER.warn("Undo action didn't complete in " + (i + 1) + "s");
         } catch (InterruptedException e) {
            LOGGER.error("Interrupted", e);
            Thread.currentThread().interrupt();
            break;
         } catch (ExecutionException e) {
            LOGGER.error("Failed to undo", e);
            break;
         }
      }

      return none();
   }

}
