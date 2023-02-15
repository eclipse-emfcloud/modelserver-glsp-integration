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

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import org.eclipse.emfcloud.integration.example.glspjavaserver.TaskListModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Identifiable;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Task;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Transition;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelState;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSOperationHandler;
import org.eclipse.glsp.server.operations.DeleteOperation;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

/**
 * Handles {@link DeleteOperation}s and delegates to the Model Server via the {@link TaskListModelServerAccess}.
 */
public class DeleteTaskListElementHandler extends EMSOperationHandler<DeleteOperation> {

   @Inject
   protected EMSNotationModelState modelState;

   @Inject
   protected TaskListModelServerAccess modelServerAccess;

   @Override
   public void executeOperation(final DeleteOperation operation) {
      operation.getElementIds().forEach(elementId -> {

         Identifiable semanticElement = getOrThrow(modelState.getIndex().getEObject(elementId),
            Identifiable.class, "Could not find element for id '" + elementId + "', no delete operation executed.");

         if (semanticElement instanceof Task) {
            modelServerAccess.removeTask((Task) semanticElement)
               .thenAccept(response -> {
                  throw new GLSPServerException(
                     "Could not execute delete operation on Layer: " + semanticElement.toString());
               });
         } else if (semanticElement instanceof Transition) {
            modelServerAccess.removeTransition((Transition) semanticElement)
               .thenAccept(response -> {
                  throw new GLSPServerException(
                     "Could not execute delete operation on Connection: " + semanticElement.toString());
               });
         }
      });
   }

}
