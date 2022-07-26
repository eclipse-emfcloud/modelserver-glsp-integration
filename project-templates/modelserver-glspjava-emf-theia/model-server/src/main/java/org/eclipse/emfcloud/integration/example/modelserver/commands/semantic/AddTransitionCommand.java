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
import org.eclipse.emfcloud.integration.example.modelserver.commands.util.SemanticCommandUtil;
import org.eclipse.emfcloud.modelserver.glsp.example.model.ModelFactory;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Task;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Transition;

public class AddTransitionCommand extends SemanticElementCommand {

   private final Transition newTransition;
   protected final Task source;
   protected final Task target;

   public AddTransitionCommand(final EditingDomain domain, final URI modelUri,
      final String sourceElementId, final String targetElementId) {
      super(domain, modelUri);
      this.newTransition = ModelFactory.eINSTANCE.createTransition();
      this.source = SemanticCommandUtil.getElement(semanticModel, sourceElementId, Task.class);
      this.target = SemanticCommandUtil.getElement(semanticModel, targetElementId, Task.class);
   }

   @Override
   protected void doExecute() {
      newTransition.setId(UUID.randomUUID().toString());
      newTransition.setSource(this.source);
      newTransition.setTarget(this.target);
      semanticModel.getTransitions().add(newTransition);
   }

   public Transition getNewTransition() { return newTransition; }

}
