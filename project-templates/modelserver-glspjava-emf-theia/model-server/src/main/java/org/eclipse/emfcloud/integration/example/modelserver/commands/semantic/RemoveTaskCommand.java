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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.integration.example.modelserver.commands.util.SemanticCommandUtil;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Task;

public class RemoveTaskCommand extends SemanticElementCommand {

   protected final String semanticElementId;

   public RemoveTaskCommand(final EditingDomain domain, final URI modelUri,
      final String semanticElementId) {
      super(domain, modelUri);
      this.semanticElementId = semanticElementId;
   }

   @Override
   protected void doExecute() {
      Task taskToRemove = SemanticCommandUtil.getElement(semanticModel, semanticElementId, Task.class);
      semanticModel.getTasks().remove(taskToRemove);
   }

}
