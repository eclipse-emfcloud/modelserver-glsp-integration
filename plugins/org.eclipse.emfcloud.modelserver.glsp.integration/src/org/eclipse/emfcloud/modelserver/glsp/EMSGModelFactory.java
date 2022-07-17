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
package org.eclipse.emfcloud.modelserver.glsp;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.builder.impl.GGraphBuilder;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

import com.google.inject.Inject;

/**
 * A graph model factory produces a graph model from the source model in the model state.
 */
public abstract class EMSGModelFactory implements GModelFactory {

   @Inject
   protected EMSModelState modelState;
   @Inject
   protected EMFIdGenerator idGenerator;

   @Override
   public void createGModel() {
      GModelRoot newRoot = createRootElement();
      modelState.updateRoot(newRoot);
      fillRootElement(newRoot);
   }

   protected GModelRoot createRootElement() {
      GGraph graph = new GGraphBuilder().build();
      graph.setId(ClientOptionsUtil.getSourceUri(modelState.getClientOptions()).orElse("root"));
      return graph;
   }

   /**
    * Fills the new root element with a graph model derived from the source model.
    *
    * @param newRoot new graph model root
    */
   protected void fillRootElement(final GModelRoot newRoot) {
      EObject semanticModel = modelState.getSemanticModel();
      modelState.getIndex().indexEObject(semanticModel);
      fillRootElement(semanticModel, newRoot);
   }

   /**
    * Fills the new root element with a graph model derived from the semantic model.
    *
    * @param semanticModel semantic model root
    * @param newRoot       new graph model root
    */
   protected abstract void fillRootElement(EObject semanticModel, GModelRoot newRoot);

}
