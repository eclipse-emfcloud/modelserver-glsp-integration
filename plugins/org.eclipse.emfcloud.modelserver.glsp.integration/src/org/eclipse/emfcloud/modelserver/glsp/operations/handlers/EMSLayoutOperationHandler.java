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
package org.eclipse.emfcloud.modelserver.glsp.operations.handlers;

import org.eclipse.emfcloud.modelserver.glsp.layout.EMSLayoutEngine;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelState;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.features.core.model.ModelSubmissionHandler;
import org.eclipse.glsp.server.layout.LayoutEngine;
import org.eclipse.glsp.server.layout.ServerLayoutKind;
import org.eclipse.glsp.server.operations.LayoutOperation;

import com.google.inject.Inject;

public class EMSLayoutOperationHandler
   extends EMSBasicOperationHandler<LayoutOperation, EMSNotationModelState, EMSNotationModelServerAccess> {

   @Inject
   protected LayoutEngine layoutEngine;
   @Inject
   protected ModelSubmissionHandler modelSubmissionHandler;
   @Inject
   protected DiagramConfiguration diagramConfiguration;

   @Override
   public void executeOperation(final LayoutOperation operation, final EMSNotationModelState modelState,
      final EMSNotationModelServerAccess modelServerAccess) {

      if (diagramConfiguration.getLayoutKind() == ServerLayoutKind.MANUAL) {
         if (layoutEngine != null && layoutEngine instanceof EMSLayoutEngine) {
            GModelElement layoutedRoot = ((EMSLayoutEngine) layoutEngine).layoutRoot(modelState);
            modelServerAccess.setLayout(modelState, layoutedRoot);
         }
      }
   }

}
