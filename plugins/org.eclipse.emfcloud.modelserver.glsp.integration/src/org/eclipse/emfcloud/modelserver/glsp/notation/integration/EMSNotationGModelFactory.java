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
package org.eclipse.emfcloud.modelserver.glsp.notation.integration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.glsp.EMSGModelFactory;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.builder.impl.GEdgeBuilder;
import org.eclipse.glsp.graph.builder.impl.GNodeBuilder;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.emf.model.notation.Shape;
import org.eclipse.glsp.server.emf.notation.util.NotationUtil;

import com.google.inject.Inject;

public abstract class EMSNotationGModelFactory extends EMSGModelFactory {

   @Inject
   protected EMSNotationModelState modelState;

   @Override
   protected void fillRootElement(final GModelRoot newRoot) {
      Diagram notationModel = modelState.getNotationModel();
      EObject semanticModel = modelState.getSemanticModel();
      modelState.getIndex().indexAll(notationModel, semanticModel);
      fillRootElement(semanticModel, notationModel, newRoot);
   }

   @Override
   protected void fillRootElement(final EObject semanticModel, final GModelRoot newRoot) {
      // no-op, is replaced by fillRootElement(EObject, Diagram, GModelRoot) for the notation use case
   }

   /**
    * Fills the new root element with a graph model derived from the semantic and the notation model.
    *
    * @param semanticModel semantic model root
    * @param notationModel notation model root
    * @param newRoot       new graph model root
    */
   protected abstract void fillRootElement(EObject semanticModel, Diagram notationModel,
      GModelRoot newRoot);

   /**
    * Applies all layout related data from the shape semantic element to the builder if possible.
    *
    * @param shapeElement element represented by a shape in the notation
    * @param builder      node builder
    * @return the given builder
    */
   protected GNodeBuilder applyShapeData(final EObject shapeElement, final GNodeBuilder builder) {
      modelState.getIndex().getNotation(shapeElement, Shape.class)
         .ifPresent(shape -> NotationUtil.applyShapeData(shape, builder));
      return builder;
   }

   /**
    * Applies all layout related data from the edge semantic element to the builder if possible.
    *
    * @param edgeElement element represented by an edge in the notation
    * @param builder     edge builder
    * @return the given builder
    */
   protected GEdgeBuilder applyEdgeData(final EObject edgeElement, final GEdgeBuilder builder) {
      modelState.getIndex().getNotation(edgeElement, Edge.class)
         .ifPresent(edge -> NotationUtil.applyEdgeData(edge, builder));
      return builder;
   }

}
