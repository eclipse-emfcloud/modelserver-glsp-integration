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

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.Diagram;
import org.eclipse.glsp.server.model.DefaultGModelState;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.GLSPServerException;

public abstract class EMSModelState extends DefaultGModelState {

   public void initialize(final Map<String, String> clientOptions,
      final EMSModelServerAccess modelServerAccess) {
      setClientOptions(clientOptions);
      setModelServerAccess(modelServerAccess);
   }

   public abstract EObject getSemanticModel();

   public abstract Diagram getNotationModel();

   public static EMSModelState getModelState(final GModelState state) {
      if (!(state instanceof EMSModelState)) {
         throw new IllegalArgumentException("Argument must be a EMSModelState");
      }
      return ((EMSModelState) state);
   }

   public static EMSModelServerAccess getModelServerAccess(final GModelState state) {
      return getModelState(state).getModelServerAccess();
   }

   protected abstract void setModelServerAccess(EMSModelServerAccess modelServerAccess);

   public abstract EMSModelServerAccess getModelServerAccess();

   public abstract void loadSourceModels() throws GLSPServerException;

}
