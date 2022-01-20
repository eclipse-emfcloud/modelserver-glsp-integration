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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.Diagram;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelState;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public class DefaultModelState extends EMSNotationModelState {

   @SuppressWarnings("rawtypes")
   @Inject
   @SemanticModelRootClass
   Class semanticModelRootClass;

   public static DefaultModelState getModelState(final GModelState state) {
      if (!(state instanceof DefaultModelState)) {
         throw new IllegalArgumentException("Argument must be a DefaultModelState");
      }
      return ((DefaultModelState) state);
   }

   protected EMSNotationModelServerAccess modelServerAccess;

   protected EObject semanticModel;
   protected Diagram notationModel;

   @Override
   public DefaultModelIndex getIndex() { return DefaultModelIndex.get(getRoot()); }

   @Override
   public EObject getSemanticModel() { return semanticModel; }

   @Override
   public Diagram getNotationModel() { return notationModel; }

   @Override
   protected void setModelServerAccess(final EMSModelServerAccess modelServerAccess) {
      this.modelServerAccess = (EMSNotationModelServerAccess) modelServerAccess;
   }

   @Override
   public EMSModelServerAccess getModelServerAccess() { return modelServerAccess; }

   @Override
   public void loadSourceModels() throws GLSPServerException {
      EObject semanticRoot = modelServerAccess.getSemanticModel();
      if (!semanticModelRootClass.isInstance(semanticRoot)) {
         throw new GLSPServerException("Error during semantic model loading");
      }
      this.semanticModel = semanticRoot;

      // initialize semantic model
      EcoreUtil.resolveAll(semanticModel);

      EObject notationRoot = modelServerAccess.getNotationModel();
      if (notationRoot != null && !(notationRoot instanceof Diagram)) {
         throw new GLSPServerException("Error during notation diagram loading");
      }
      this.notationModel = (Diagram) notationRoot;
   }

}
