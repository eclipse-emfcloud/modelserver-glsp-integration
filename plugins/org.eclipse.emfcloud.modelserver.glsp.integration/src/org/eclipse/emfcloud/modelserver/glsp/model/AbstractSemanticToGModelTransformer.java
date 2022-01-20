/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp.model;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelState;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public abstract class AbstractSemanticToGModelTransformer<T extends EObject, E extends GModelElement> {

   @Inject
   GModelState gModelState;

   protected EMSNotationModelState getModelState() { return EMSNotationModelState.getModelState(gModelState); }

   public abstract E create(T semanticElement);

   public <U extends E> Optional<U> create(final T semanticElement, final Class<U> clazz) {
      return Optional.ofNullable(create(semanticElement)).filter(clazz::isInstance).map(clazz::cast);
   }

   protected String toId(final EObject semanticElement) {
      String id = getModelState().getIndex().getSemanticId(semanticElement).orElse(null);
      if (id == null) {
         id = EcoreUtil.getURI(semanticElement).fragment();
         getModelState().getIndex().indexSemantic(id, semanticElement);
      }
      return id;

   }
}
