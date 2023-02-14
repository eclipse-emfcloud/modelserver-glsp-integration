/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
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
import java.util.Optional;

import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSOperationHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;

public class EMSOperationActionHandler extends OperationActionHandler {

   @Override
   public List<Action> executeAction(final Operation operation) {
      // Disable the special handling for CreateOperation, as we don't register
      // one handler per element type to create.
      Optional<? extends OperationHandler<?>> operationHandler = operationHandlerRegistry.get(operation);
      if (operationHandler.isPresent()) {
         return executeHandler(operation, operationHandler.get());
      }
      return none();
   }

   @Override
   protected List<Action> executeHandler(final Operation operation, final OperationHandler<?> handler) {
      if (handler instanceof EMSOperationHandler) {
         handler.execute(operation);
      }
      return none();
   }

}
