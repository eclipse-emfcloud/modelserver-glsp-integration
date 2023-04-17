/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.emf.EMFModelIndex;
import org.eclipse.glsp.server.emf.EMFModelStateImpl;
import org.eclipse.glsp.server.emf.notation.EMFSemanticIdConverter;
import org.eclipse.glsp.server.session.ClientSession;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class EMSModelStateImpl extends EMFModelStateImpl implements EMSModelState {

   private static final Logger LOGGER = LogManager.getLogger(EMSModelStateImpl.class);

   protected EObject semanticModel;

   @Inject
   protected EMSModelServerAccess modelServerAccess;
   @Inject
   protected EMFSemanticIdConverter semanticIdConverter;

   @Override
   protected GModelIndex getOrUpdateIndex(final GModelRoot newRoot) {
      return EMFModelIndex.getOrCreate(getRoot(), semanticIdConverter);
   }

   @Override
   public void setSemanticModel(final EObject semanticModel) { this.semanticModel = semanticModel; }

   @Override
   public EObject getSemanticModel() { return this.semanticModel; }

   @Override
   public <T extends EObject> Optional<T> getSemanticModel(final Class<T> clazz) {
      return Optional.ofNullable(this.semanticModel).filter(clazz::isInstance).map(clazz::cast);
   }

   @Override
   public void sessionDisposed(final ClientSession clientSession) {
      if (this.semanticModel != null) {
         this.semanticModel = null;
         modelServerAccess.close().thenAccept(response -> {
            if (!response.body()) {
               LOGGER.warn("Error on disposing ClientSession: " + response.getMessage());
            }
         });
         modelServerAccess.unsubscribe();
      }
      super.sessionDisposed(clientSession);
   }

}
