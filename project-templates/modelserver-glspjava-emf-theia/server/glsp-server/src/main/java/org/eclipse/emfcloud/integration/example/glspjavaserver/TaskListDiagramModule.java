/********************************************************************************
 * Copyright (c) 2022-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.integration.example.glspjavaserver;

import org.eclipse.emfcloud.integration.example.glspjavaserver.handler.CreateTaskNodeHandler;
import org.eclipse.emfcloud.integration.example.glspjavaserver.handler.CreateTransitionEdgeHandler;
import org.eclipse.emfcloud.integration.example.glspjavaserver.handler.DeleteTaskListElementHandler;
import org.eclipse.emfcloud.integration.example.glspjavaserver.model.TaskListGModelFactory;
import org.eclipse.emfcloud.integration.example.glspjavaserver.palette.TaskListToolPaletteItemProvider;
import org.eclipse.emfcloud.modelserver.glsp.example.model.ModelPackage;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSGLSPNotationDiagramModule;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;
import org.eclipse.glsp.server.operations.OperationHandler;

public class TaskListDiagramModule extends EMSGLSPNotationDiagramModule {

   @Override
   protected void registerEPackages() {
      // register and initialize all used ePackages
      super.registerEPackages();
      ModelPackage.eINSTANCE.eClass();
   }

   @Override
   protected Class<? extends DiagramConfiguration> bindDiagramConfiguration() {
      // define what operations are allowed with our elements
      return TaskListDiagramConfiguration.class;
   }

   @Override
   public Class<? extends GModelFactory> bindGModelFactory() {
      // custom factory to convert tasks into nodes
      return TaskListGModelFactory.class;
   }

   @Override
   protected Class<? extends ToolPaletteItemProvider> bindToolPaletteItemProvider() {
      // custom provider for tool palette items
      return TaskListToolPaletteItemProvider.class;
   }

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler<?>> binding) {
      // custom operation handler to create elements
      super.configureOperationHandlers(binding);
      binding.add(CreateTaskNodeHandler.class);
      binding.add(CreateTransitionEdgeHandler.class);
      binding.add(DeleteTaskListElementHandler.class);
   }

   @Override
   protected Class<? extends EMSNotationModelServerAccess> bindModelServerAccess() {
      return TaskListModelServerAccess.class;
   }

   @Override
   public String getDiagramType() { return "tasklist-diagram"; }

   @Override
   protected String getSemanticFileExtension() { return "tasklist"; }

   @Override
   protected String getNotationFileExtension() { return "notation"; }

}
