/********************************************************************************
 * Copyright (c) 2022-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp.operations.handlers;

import java.util.Optional;

import org.eclipse.glsp.server.operations.CompoundOperation;
import org.eclipse.glsp.server.operations.Operation;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.OperationHandlerRegistry;

import com.google.inject.Inject;

public class EMSCompoundOperationHandler extends EMSOperationHandler<CompoundOperation> {

   @Inject
   protected OperationHandlerRegistry operationHandlerRegistry;

   @Override
   protected void executeOperation(final CompoundOperation operation) {
      operation.getOperationList().forEach(nestedOperation -> executeNestedOperation(nestedOperation));
   }

   @SuppressWarnings("rawtypes")
   protected void executeNestedOperation(final Operation operation) {
      Optional<? extends OperationHandler> operationHandler = operationHandlerRegistry.getOperationHandler(operation);
      if (operationHandler.isPresent()) {
         if (operationHandler.get() instanceof EMSOperationHandler) {
            ((EMSOperationHandler) operationHandler.get()).execute(operation);
         } else {
            operationHandler.get().execute(operation);
         }
      }
   }

}
