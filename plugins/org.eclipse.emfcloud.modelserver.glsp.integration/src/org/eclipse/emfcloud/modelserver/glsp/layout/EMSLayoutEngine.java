/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp.layout;

import org.eclipse.elk.alg.layered.options.FixedAlignment;
import org.eclipse.elk.alg.layered.options.LayeredOptions;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelState;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.layout.ElkLayoutEngine;
import org.eclipse.glsp.layout.GLSPLayoutConfigurator;

public class EMSLayoutEngine extends ElkLayoutEngine {

   @Override
   public void layout() {
      // no-op
   }

   public GModelElement layoutRoot(final EMSNotationModelState modelState) {
      GModelElement newRoot = EcoreUtil.copy(modelState.getRoot());
      if (newRoot instanceof GGraph) {
         GLSPLayoutConfigurator configurator = new GLSPLayoutConfigurator();
         configureLayoutOptions(configurator);
         this.layout((GGraph) newRoot, configurator);
      }
      return newRoot;
   }

   protected void configureLayoutOptions(final GLSPLayoutConfigurator configurator) {
      // ELK Layered Algorithm Reference:
      // https://www.eclipse.org/elk/reference/algorithms/org-eclipse-elk-layered.html
      configurator.configureByType(DefaultTypes.GRAPH)
         .setProperty(LayeredOptions.NODE_PLACEMENT_BK_FIXED_ALIGNMENT, FixedAlignment.BALANCED);
   }

}
