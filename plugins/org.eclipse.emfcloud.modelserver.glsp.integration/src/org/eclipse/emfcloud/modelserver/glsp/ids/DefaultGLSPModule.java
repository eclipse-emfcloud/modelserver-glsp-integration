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
import org.eclipse.emfcloud.modelserver.glsp.model.RootSemanticToGModelTransformer;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationGLSPModule;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.features.core.model.ModelSourceLoader;

public abstract class DefaultGLSPModule extends EMSNotationGLSPModule {

   @Override
   protected void configure() {
      bind(String.class).annotatedWith(SemanticModelExtension.class).toInstance(getSemanticModelExtension());
      bind(Class.class).annotatedWith(SemanticModelRootClass.class).toInstance(getSemanticModelRootClass());
      bind(RootSemanticToGModelTransformer.class).to(bindRootSemanticToGModelTransformer());
      super.configure();
   }

   @Override
   protected Class<? extends ModelSourceLoader> bindSourceModelLoader() {
      return DefaultModelSourceLoader.class;
   }

   @Override
   protected Class<? extends GModelFactory> bindGModelFactory() {
      return DefaultModelFactory.class;
   }

   @Override
   protected Class<DefaultModelState> bindGModelState() {
      return DefaultModelState.class;
   }

   protected abstract Class<? extends RootSemanticToGModelTransformer<? extends EObject>> bindRootSemanticToGModelTransformer();

   protected abstract String getSemanticModelExtension();

   protected abstract Class<? extends EObject> getSemanticModelRootClass();

}
