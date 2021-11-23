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
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSRefreshModelActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSSaveModelActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.actions.handlers.EMSUndoActionHandler;
import org.eclipse.emfcloud.modelserver.glsp.layout.EMSLayoutEngine;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.glsp.server.actions.ActionHandler;
import org.eclipse.glsp.server.actions.SaveModelActionHandler;
import org.eclipse.glsp.server.di.GModelJsonDiagramModule;
import org.eclipse.glsp.server.di.MultiBinding;
import org.eclipse.glsp.server.features.undoredo.UndoRedoActionHandler;
import org.eclipse.glsp.server.layout.LayoutEngine;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.OperationActionHandler;

public abstract class EMSGLSPModule extends GModelJsonDiagramModule {

   @Override
   protected void configureActionHandlers(final MultiBinding<ActionHandler> bindings) {
      super.configureActionHandlers(bindings);
      bindings.rebind(SaveModelActionHandler.class, EMSSaveModelActionHandler.class);
      bindings.rebind(OperationActionHandler.class, EMSOperationActionHandler.class);
      bindings.remove(UndoRedoActionHandler.class);
      bindings.add(EMSUndoActionHandler.class);
      bindings.add(EMSRedoActionHandler.class);
      bindings.add(EMSRefreshModelActionHandler.class);
   }

   @Override
   protected void configureAdditionals() {
      super.configureAdditionals();
   }

   @Override
   protected Class<? extends LayoutEngine> bindLayoutEngine() {
      return EMSLayoutEngine.class;
   }

   @Override
   protected Class<? extends GModelState> bindGModelState() {
      return EMSModelState.class;
   }

}
