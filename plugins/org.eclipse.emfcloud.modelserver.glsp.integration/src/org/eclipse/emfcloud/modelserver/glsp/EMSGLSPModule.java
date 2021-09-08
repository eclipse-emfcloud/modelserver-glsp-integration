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
package org.eclipse.emfcloud.modelserver.glsp;

import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSOperationActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSRedoActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSSaveModelActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSUndoActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.client.ModelServerClientProvider;
import org.eclipse.emfcloud.modelserver.glsp.layout.EMSLayoutEngine;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.SaveModelActionHandler;
import org.eclipse.glsp.server.di.DefaultGLSPModule;
import org.eclipse.glsp.server.features.undoredo.UndoRedoActionHandler;
import org.eclipse.glsp.server.layout.ILayoutEngine;
import org.eclipse.glsp.server.operations.OperationActionHandler;
import org.eclipse.glsp.server.protocol.GLSPServer;
import org.eclipse.glsp.server.utils.MultiBinding;

public abstract class EMSGLSPModule extends DefaultGLSPModule {

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> bindings) {
      super.configureActionHandlers(bindings);
      bindings.rebind(SaveModelActionHandler.class, EMSSaveModelActionHandler.class);
      bindings.rebind(OperationActionHandler.class, EMSOperationActionHandler.class);
      bindings.remove(UndoRedoActionHandler.class);
      bindings.add(EMSUndoActionHandler.class);
      bindings.add(EMSRedoActionHandler.class);
   }

   @Override
   public void configure() {
      super.configure();
      bind(bindModelServerClientProvider()).asEagerSingleton();
   }

   @Override
   protected Class<? extends GLSPServer> bindGLSPServer() {
      return EMSGLSPServer.class;
   }

   protected Class<? extends ModelServerClientProvider> bindModelServerClientProvider() {
      return ModelServerClientProvider.class;
   }

   @Override
   protected Class<? extends ILayoutEngine> bindLayoutEngine() {
      return EMSLayoutEngine.class;
   }

}
