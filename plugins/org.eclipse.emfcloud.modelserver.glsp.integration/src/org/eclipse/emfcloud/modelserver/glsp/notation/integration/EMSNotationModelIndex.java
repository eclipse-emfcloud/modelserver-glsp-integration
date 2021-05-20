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
package org.eclipse.emfcloud.modelserver.glsp.notation.integration;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.glsp.notation.NotationElement;
import org.eclipse.glsp.graph.impl.GModelIndexImpl;

public abstract class EMSNotationModelIndex extends GModelIndexImpl {

   protected EMSNotationModelIndex(final EObject target) {
      super(target);
   }

   @Override
   public abstract boolean isAdapterForType(Object type);

   public abstract void clear();

   public abstract void indexSemantic(String id, EObject semanticElement);

   public abstract void indexNotation(NotationElement notationElement);

   public abstract <T extends EObject> Optional<T> getSemantic(String id, Class<T> clazz);

   public abstract <T extends NotationElement> Optional<T> getNotation(String id, Class<T> clazz);

}
