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

import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV2;
import org.eclipse.emfcloud.modelserver.glsp.EMSGLSPDiagramModule;
import org.eclipse.emfcloud.modelserver.glsp.ModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.layout.EMSLayoutEngine;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSChangeBoundsOperationHandler;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSChangeRoutingPointsOperationHandler;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSLayoutOperationHandler;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationFileExtension;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationModelFormat;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;
import org.eclipse.glsp.server.layout.LayoutEngine;
import org.eclipse.glsp.server.operations.LayoutOperationHandler;
import org.eclipse.glsp.server.operations.OperationHandler;

import com.google.inject.Singleton;

public abstract class EMSGLSPNotationDiagramModule extends EMSGLSPDiagramModule {

   @Override
   protected void configureBase() {
      super.configureBase();
      bind(String.class).annotatedWith(NotationFileExtension.class).toInstance(getNotationFileExtension());
      bind(String.class).annotatedWith(NotationModelFormat.class).toInstance(getNotationModelFormat());
   }

   @Override
   protected void registerEPackages() {
      super.registerEPackages();
      NotationPackage.eINSTANCE.eClass();
   }

   protected abstract String getNotationFileExtension();

   protected String getNotationModelFormat() { return ModelServerPathParametersV2.FORMAT_JSON_V2; }

   @Override
   protected Class<? extends EMSNotationModelState> bindGModelState() {
      return EMSNotationModelStateImpl.class;
   }

   @Override
   protected Class<? extends EMSNotationSourceModelStorage> bindSourceModelStorage() {
      return EMSNotationSourceModelStorage.class;
   }

   @Override
   @SuppressWarnings("unchecked")
   protected void configureModelServerAccess(final Class<? extends ModelServerAccess> modelServerAccessClass) {
      super.configureModelServerAccess(modelServerAccessClass);
      bind(EMSNotationModelServerAccess.class)
         .to((Class<? extends EMSNotationModelServerAccess>) modelServerAccessClass);
   }

   @Override
   protected Class<? extends EMSNotationModelServerAccess> bindModelServerAccess() {
      return EMSNotationModelServerAccess.class;
   }

   @Override
   protected Class<? extends LayoutEngine> bindLayoutEngine() {
      return EMSLayoutEngine.class;
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler<?>> binding) {
      super.configureOperationHandlers(binding);
      binding.rebind(LayoutOperationHandler.class, EMSLayoutOperationHandler.class);
      binding.add(EMSChangeBoundsOperationHandler.class);
      binding.add(EMSChangeRoutingPointsOperationHandler.class);
   }

   @Override
   protected void configure() {
      super.configure();
      configureEMSNotationModelState(bindGModelState());
   }

   protected void configureEMSNotationModelState(final Class<? extends EMSNotationModelState> modelState) {
      bind(EMSNotationModelState.class).to(modelState).in(Singleton.class);
   }

}
