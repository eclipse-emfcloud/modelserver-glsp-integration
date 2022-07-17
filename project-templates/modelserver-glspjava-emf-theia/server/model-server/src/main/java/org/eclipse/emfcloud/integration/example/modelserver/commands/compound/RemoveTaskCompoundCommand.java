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
package org.eclipse.emfcloud.integration.example.modelserver.commands.compound;

import java.util.Collection;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.integration.example.modelserver.commands.semantic.RemoveTaskCommand;
import org.eclipse.emfcloud.integration.example.modelserver.commands.util.SemanticCommandUtil;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Task;
import org.eclipse.emfcloud.modelserver.glsp.example.model.TaskList;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Transition;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.RemoveNotationElementCommand;

public class RemoveTaskCompoundCommand extends CompoundCommand {

   public RemoveTaskCompoundCommand(final EditingDomain domain, final URI modelUri, final String semanticElementId) {

      // Remove semantic and notation element
      this.append(new RemoveTaskCommand(domain, modelUri, semanticElementId));
      this.append(new RemoveNotationElementCommand(domain, modelUri, semanticElementId));

      // Check for usages of Tasks, as source/target of Transitions, and remove Transitions if applicable
      TaskList taskListModel = SemanticCommandUtil.getModel(modelUri, domain);
      Task taskToRemove = SemanticCommandUtil.getElement(taskListModel, semanticElementId, Task.class);

      Collection<Setting> usagesTask = UsageCrossReferencer.find(taskToRemove, taskListModel.eResource());
      for (Setting setting : usagesTask) {
         EObject eObject = setting.getEObject();
         if (eObject instanceof Transition) {
            String transitionElementId = EcoreUtil.getID(eObject);
            this.append(new RemoveTransitionCompoundCommand(domain, modelUri, transitionElementId));
         }
      }
   }

}
