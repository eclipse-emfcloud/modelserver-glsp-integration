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
package org.eclipse.emfcloud.integration.example.glspjavaserver;

import java.util.concurrent.CompletableFuture;

import org.eclipse.emfcloud.integration.example.modelserver.contributions.AddTaskCommandContribution;
import org.eclipse.emfcloud.integration.example.modelserver.contributions.AddTransitionCommandContribution;
import org.eclipse.emfcloud.integration.example.modelserver.contributions.RemoveTaskCommandContribution;
import org.eclipse.emfcloud.integration.example.modelserver.contributions.RemoveTransitionCommandContribution;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Task;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Transition;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.glsp.graph.GPoint;

public class TaskListModelServerAccess extends EMSNotationModelServerAccess {

   /*
    * Task
    */
   public CompletableFuture<Response<String>> addTask(final GPoint newPosition) {

      CCompoundCommand compoundCommand = AddTaskCommandContribution.create(newPosition);
      return this.edit(compoundCommand);
   }

   public CompletableFuture<Response<String>> removeTask(final Task taskToRemove) {
      CCompoundCommand compoundCommand = RemoveTaskCommandContribution.create(idGenerator.getOrCreateId(taskToRemove));
      return this.edit(compoundCommand);
   }

   /*
    * Transition
    */
   public CompletableFuture<Response<String>> addTransition(final String sourceId, final String targetId) {

      CCompoundCommand compoundCommand = AddTransitionCommandContribution.create(sourceId, targetId);
      return this.edit(compoundCommand);
   }

   public CompletableFuture<Response<String>> removeTransition(final Transition transitionToRemove) {

      CCompoundCommand compoundCommand = RemoveTransitionCommandContribution
         .create(idGenerator.getOrCreateId(transitionToRemove));
      return this.edit(compoundCommand);
   }

}
