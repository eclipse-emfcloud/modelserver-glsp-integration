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
package org.eclipse.emfcloud.modelserver.glsp.layout;

import org.eclipse.elk.graph.ElkGraphElement;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.layout.ElkLayoutEngine;
import org.eclipse.glsp.server.model.GModelState;

public class EMSLayoutEngine extends ElkLayoutEngine {

   @Override
   public void layout(final GModelState modelState) {
      // no-op
   }

   public GModelElement layoutRoot(final GModelState modelState) {
      GModelElement newRoot = EcoreUtil.copy(modelState.getRoot());
      if (newRoot instanceof GGraph) {
         this.layout((GGraph) newRoot, null);
      }
      return newRoot;
   }

   @Override
   protected boolean shouldInclude(final GModelElement element, final GModelElement parent,
      final ElkGraphElement elkParent, final LayoutContext context) {

      if (element.getType().equals("label:icon")) {
         return false;
      } else if (element instanceof GLabel && parent instanceof GEdge) {
         return false;
      }
      return super.shouldInclude(element, parent, elkParent, context);
   }

}
