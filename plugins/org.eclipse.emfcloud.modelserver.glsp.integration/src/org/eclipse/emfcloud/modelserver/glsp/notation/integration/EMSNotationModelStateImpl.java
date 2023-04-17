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
package org.eclipse.emfcloud.modelserver.glsp.notation.integration;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelStateImpl;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.notation.EMFNotationModelIndex;
import org.eclipse.glsp.server.session.ClientSession;

import com.google.inject.Singleton;

@Singleton
public class EMSNotationModelStateImpl extends EMSModelStateImpl implements EMSNotationModelState {

   protected Diagram notationModel;

   @Override
   protected GModelIndex getOrUpdateIndex(final GModelRoot newRoot) {
      return EMFNotationModelIndex.getOrCreate(getRoot(), semanticIdConverter);
   }

   @Override
   public EMFNotationModelIndex getIndex() { return (EMFNotationModelIndex) super.getIndex(); }

   @Override
   public void setNotationModel(final Diagram notationModel) { this.notationModel = notationModel; }

   @Override
   public Diagram getNotationModel() { return this.notationModel; }

   @Override
   public <T extends EObject> Optional<T> getNotationModel(final Class<T> clazz) {
      return Optional.ofNullable(this.notationModel).filter(clazz::isInstance).map(clazz::cast);
   }

   @Override
   public void sessionDisposed(final ClientSession clientSession) {
      this.notationModel = null;
      super.sessionDisposed(clientSession);
   }

}
