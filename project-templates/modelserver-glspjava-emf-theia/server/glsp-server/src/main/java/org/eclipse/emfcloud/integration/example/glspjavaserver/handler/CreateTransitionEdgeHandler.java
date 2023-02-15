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
package org.eclipse.emfcloud.integration.example.glspjavaserver.handler;

import org.eclipse.emfcloud.integration.example.glspjavaserver.TaskListModelServerAccess;
import org.eclipse.emfcloud.integration.example.glspjavaserver.TaskListModelTypes;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSCreateOperationHandler;
import org.eclipse.glsp.server.operations.CreateEdgeOperation;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

/**
 * Handles {@link CreateEdgeOperation}s and delegates to the Model Server via the {@link TaskListModelServerAccess}.
 */
public class CreateTransitionEdgeHandler extends EMSCreateOperationHandler<CreateEdgeOperation> {

   @Inject
   protected TaskListModelServerAccess modelAccess;

   public CreateTransitionEdgeHandler() {
      super(TaskListModelTypes.TRANSITION);
   }

   @Override
   public void executeOperation(final CreateEdgeOperation operation) {

      switch (operation.getElementTypeId()) {
         case TaskListModelTypes.TRANSITION: {
            String sourceId = operation.getSourceElementId();
            String targetId = operation.getTargetElementId();

            modelAccess.addTransition(sourceId, targetId)
               .thenAccept(response -> {
                  if (response.body() == null || response.body().isEmpty()) {
                     throw new GLSPServerException("Could not execute create operation on new Transition edge");
                  }
               });
            break;
         }
         default:
            throw new GLSPServerException(
               "Could not execute create operation for edge with type " + operation.getElementTypeId() + "!");
      }
   }

   @Override
   public String getLabel() { return "Create Transition"; }

}
