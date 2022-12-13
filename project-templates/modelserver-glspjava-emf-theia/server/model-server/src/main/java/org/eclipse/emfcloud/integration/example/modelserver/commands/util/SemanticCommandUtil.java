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
package org.eclipse.emfcloud.integration.example.modelserver.commands.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.integration.example.modelserver.TaskListResource;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Identifiable;
import org.eclipse.emfcloud.modelserver.glsp.example.model.TaskList;

public final class SemanticCommandUtil {

   private SemanticCommandUtil() {}

   public static EObject getElement(final EObject container, final String semanticElementId) {
      return container.eResource().getEObject(semanticElementId);
   }

   public static <C extends Identifiable> C getElement(final TaskList model, final String semanticElementId,
      final java.lang.Class<C> clazz) {
      EObject element = getElement(model, semanticElementId);
      return clazz.cast(element);
   }

   public static TaskList getModel(final URI modelUri, final EditingDomain domain) {
      Resource semanticResource = domain.getResourceSet()
         .getResource(
            modelUri.trimFileExtension().appendFileExtension(TaskListResource.FILE_EXTENSION), false);
      EObject semanticRoot = semanticResource.getContents().get(0);
      if (!(semanticRoot instanceof TaskList)) {}
      return (TaskList) semanticRoot;
   }

}
