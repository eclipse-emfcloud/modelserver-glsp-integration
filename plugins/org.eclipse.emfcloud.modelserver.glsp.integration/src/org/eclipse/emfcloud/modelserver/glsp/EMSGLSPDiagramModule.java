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

import org.eclipse.emfcloud.modelserver.command.CCommandPackage;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV2;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSOperationActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSRedoActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSRefreshModelActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSRequestModelActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSSaveModelActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSUndoActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSCompoundOperationHandler;
import org.eclipse.emfcloud.modelserver.integration.SemanticFileExtension;
import org.eclipse.emfcloud.modelserver.integration.SemanticModelFormat;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.SaveModelActionHandler;
import org.eclipse.glsp.server.di.DiagramModule;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.emf.idgen.AttributeIdGenerator;
import org.eclipse.glsp.server.emf.notation.EMFSemanticIdConverter;
import org.eclipse.glsp.server.features.core.model.RequestModelActionHandler;
import org.eclipse.glsp.server.features.undoredo.UndoRedoActionHandler;
import org.eclipse.glsp.server.operations.CompoundOperationHandler;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.operations.OperationHandler;

import com.google.inject.Singleton;

public abstract class EMSGLSPDiagramModule extends DiagramModule {

   @Override
   protected void configureBase() {
      super.configureBase();
      // Model-related bindings
      configureModelServerAccess(bindModelServerAccess());
      bind(String.class).annotatedWith(SemanticFileExtension.class).toInstance(getSemanticFileExtension());
      bind(String.class).annotatedWith(SemanticModelFormat.class).toInstance(getSemanticModelFormat());
      configureEMFIdGenerator(bindEMFIdGenerator());
      configureEMFSemanticIdConverter(bindEMFSemanticIdConverter());
      registerEPackages();
   }

   protected void registerEPackages() {
      // register and initialize all used ePackages
      CCommandPackage.eINSTANCE.eClass();
   }

   protected abstract String getSemanticFileExtension();

   protected String getSemanticModelFormat() { return ModelServerPathParametersV2.FORMAT_JSON_V2; }

   protected Class<? extends EMFIdGenerator> bindEMFIdGenerator() {
      // we expect all our elements inherit from Identifiable and have an ID attribute set
      return AttributeIdGenerator.class;
   }

   protected void configureEMFIdGenerator(final Class<? extends EMFIdGenerator> generatorClass) {
      bind(EMFIdGenerator.class).to(generatorClass).in(Singleton.class);
   }

   protected Class<? extends EMFSemanticIdConverter> bindEMFSemanticIdConverter() {
      return EMFSemanticIdConverter.Default.class;
   }

   protected void configureEMFSemanticIdConverter(
      final Class<? extends EMFSemanticIdConverter> converterClass) {
      bind(converterClass).in(Singleton.class);
      bind(EMFSemanticIdConverter.class).to(converterClass);
   }

   @Override
   protected Class<? extends EMSModelState> bindGModelState() {
      return EMSModelStateImpl.class;
   }

   @Override
   protected Class<? extends EMSSourceModelStorage> bindSourceModelStorage() {
      return EMSSourceModelStorage.class;
   }

   @SuppressWarnings("unchecked")
   protected void configureModelServerAccess(final Class<? extends ModelServerAccess> modelServerAccessClass) {
      bind(modelServerAccessClass).in(Singleton.class);
      bind(ModelServerAccess.class).to(modelServerAccessClass);
      bind(EMSModelServerAccess.class).to((Class<? extends EMSModelServerAccess>) modelServerAccessClass);
   }

   protected Class<? extends EMSModelServerAccess> bindModelServerAccess() {
      return EMSModelServerAccess.class;
   }

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> bindings) {
      super.configureActionHandlers(bindings);
      bindings.rebind(OperationActionHandler.class, EMSOperationActionHandler.class);
      bindings.rebind(RequestModelActionHandler.class, EMSRequestModelActionHandler.class);
      bindings.rebind(SaveModelActionHandler.class, EMSSaveModelActionHandler.class);
      bindings.remove(UndoRedoActionHandler.class);
      bindings.add(EMSUndoActionHandler.class);
      bindings.add(EMSRedoActionHandler.class);
      bindings.add(EMSRefreshModelActionHandler.class);
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler<?>> bindings) {
      super.configureOperationHandlers(bindings);
      bindings.rebind(CompoundOperationHandler.class, EMSCompoundOperationHandler.class);
   }

   @Override
   protected void configure() {
      super.configure();
      configureEMSModelState(bindGModelState());
   }

   protected void configureEMSModelState(final Class<? extends EMSModelState> modelState) {
      bind(EMSModelState.class).to(modelState).in(Singleton.class);
   }

}
