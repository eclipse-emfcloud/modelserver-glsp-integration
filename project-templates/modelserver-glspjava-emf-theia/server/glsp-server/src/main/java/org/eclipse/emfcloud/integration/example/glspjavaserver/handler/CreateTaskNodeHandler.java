/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
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
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.AbstractEMSCreateNodeOperationHandler;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.operations.CreateNodeOperation;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

/**
 * Handles {@link CreateNodeOperation}s and delegates to the Model Server via the {@link TaskListModelServerAccess}.
 */
public class CreateTaskNodeHandler extends AbstractEMSCreateNodeOperationHandler {

   @Inject
   protected TaskListModelServerAccess modelAccess;

   public CreateTaskNodeHandler() {
      super(TaskListModelTypes.TASK);
   }

   @Override
   public void executeOperation(final CreateNodeOperation operation) {

      switch (operation.getElementTypeId()) {
         case TaskListModelTypes.TASK: {

            modelAccess.addTask(operation.getLocation().orElse(GraphUtil.point(0, 0)))
               .thenAccept(response -> {
                  if (response.body() == null || response.body().isEmpty()) {
                     throw new GLSPServerException("Could not execute create operation on new Task node");
                  }
               });
            break;
         }
         default:
            throw new GLSPServerException(
               "Could not execute create operation for node with type " + operation.getElementTypeId() + "!");
      }
   }

   @Override
   public String getLabel() { return "Create Task"; }

}
