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
package org.eclipse.emfcloud.modelserver.glsp.notation.integration;

import org.eclipse.emfcloud.modelserver.glsp.EMSGLSPModule;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSChangeBoundsOperationHandler;
import org.eclipse.emfcloud.modelserver.glsp.operations.handlers.EMSLayoutOperationHandler;
import org.eclipse.glsp.server.operations.OperationHandler;
import org.eclipse.glsp.server.operations.gmodel.ChangeBoundsOperationHandler;
import org.eclipse.glsp.server.operations.gmodel.LayoutOperationHandler;
import org.eclipse.glsp.server.utils.MultiBinding;

public abstract class EMSNotationGLSPModule extends EMSGLSPModule {

   @Override
   protected void configureOperationHandlers(final MultiBinding<OperationHandler> binding) {
      super.configureOperationHandlers(binding);
      binding.rebind(ChangeBoundsOperationHandler.class, EMSChangeBoundsOperationHandler.class);
      binding.rebind(LayoutOperationHandler.class, EMSLayoutOperationHandler.class);
   }

}
