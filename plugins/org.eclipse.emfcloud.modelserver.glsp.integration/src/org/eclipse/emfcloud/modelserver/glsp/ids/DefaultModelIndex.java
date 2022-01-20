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
package org.eclipse.emfcloud.modelserver.glsp.ids;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.modelserver.glsp.notation.Diagram;
import org.eclipse.emfcloud.modelserver.glsp.notation.NotationElement;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelIndex;
import org.eclipse.glsp.graph.GModelElement;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class DefaultModelIndex extends EMSNotationModelIndex {

   public static DefaultModelIndex get(final GModelElement element) {
      EObject root = EcoreUtil.getRootContainer(element);
      DefaultModelIndex existingIndex = (DefaultModelIndex) EcoreUtil.getExistingAdapter(root,
         DefaultModelIndex.class);
      return Optional.ofNullable(existingIndex).orElseGet(() -> (create(element)));
   }

   public static DefaultModelIndex create(final GModelElement element) {
      return new DefaultModelIndex(EcoreUtil.getRootContainer(element));
   }

   private final BiMap<String, EObject> semanticIndex;
   private final BiMap<EObject, NotationElement> notationIndex;

   protected DefaultModelIndex(final EObject target) {
      super(target);
      this.semanticIndex = HashBiMap.create();
      this.notationIndex = HashBiMap.create();
   }

   @Override
   public boolean isAdapterForType(final Object type) {
      return this.getClass().equals(type);
   }

   @Override
   public void clear() {
      this.semanticIndex.clear();
      this.notationIndex.clear();
   }

   @Override
   public void indexSemantic(final String id, final EObject semanticElement) {
      semanticIndex.putIfAbsent(id, semanticElement);
   }

   @Override
   public void indexNotation(final NotationElement notationElement) {
      if (notationElement.getSemanticElement() != null) {
         EObject semanticElement = notationElement.getSemanticElement().getResolvedElement();
         notationIndex.put(semanticElement, notationElement);
         semanticIndex.inverse().putIfAbsent(semanticElement, EcoreUtil.getURI(semanticElement).fragment());
      }

      if (notationElement instanceof Diagram) {
         ((Diagram) notationElement).getElements().forEach(this::indexNotation);
      }
   }

   protected <T> Optional<T> safeCast(final Optional<?> toCast, final Class<T> clazz) {
      return toCast.filter(clazz::isInstance).map(clazz::cast);
   }

   @Override
   public <T extends NotationElement> Optional<T> getNotation(final String id, final Class<T> clazz) {
      return safeCast(getSemantic(id).flatMap(this::getNotation), clazz);
   }

   protected Optional<NotationElement> getNotation(final EObject semanticElement) {
      return Optional.ofNullable(notationIndex.get(semanticElement));
   }

   @Override
   public <T extends NotationElement> Optional<T> getNotation(final EObject semanticElement, final Class<T> clazz) {
      return safeCast(getNotation(semanticElement), clazz);
   }

   @Override
   public <T extends EObject> Optional<T> getSemantic(final String id, final Class<T> clazz) {
      return safeCast(Optional.ofNullable(semanticIndex.get(id)), clazz);
   }

   protected Optional<EObject> getSemantic(final String id) {
      return Optional.ofNullable(semanticIndex.get(id));
   }

   protected Optional<EObject> getSemantic(final GModelElement gModelElement) {
      return getSemantic(gModelElement.getId());
   }

   @Override
   public Optional<String> getSemanticId(final EObject semanticElement) {
      return Optional.ofNullable(semanticIndex.inverse().get(semanticElement));
   }
}
