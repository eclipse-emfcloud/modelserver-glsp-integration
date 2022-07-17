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
package org.eclipse.emfcloud.integration.example.modelserver.commands.semantic;

import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.glsp.example.model.ModelFactory;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Task;

public class AddTaskCommand extends SemanticElementCommand {

   protected final Task newTask;

   public AddTaskCommand(final EditingDomain domain, final URI modelUri) {
      super(domain, modelUri);
      this.newTask = ModelFactory.eINSTANCE.createTask();
   }

   @Override
   protected void doExecute() {
      newTask.setId(UUID.randomUUID().toString());
      newTask.setName("New Task");
      semanticModel.getTasks().add(newTask);
   }

   public Task getNewTask() { return newTask; }

}
