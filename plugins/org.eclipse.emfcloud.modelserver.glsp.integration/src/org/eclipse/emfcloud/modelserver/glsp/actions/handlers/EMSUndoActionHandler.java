/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
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
import java.util.concurrent.CompletionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.features.undoredo.UndoAction;

public class EMSUndoActionHandler extends AbstractEMSActionHandler<UndoAction> {

   private static final Logger LOGGER = LogManager.getLogger(EMSUndoActionHandler.class);

   @Override
   public List<Action> executeAction(final UndoAction action) {

      modelServerAccess.undo().whenComplete((msg, ex) -> {
         if (ex instanceof CompletionException) {
            // Cannot undo, print warning message
            LOGGER.warn(ex.getCause().getMessage());
         }
      });

      return none();
   }
}
