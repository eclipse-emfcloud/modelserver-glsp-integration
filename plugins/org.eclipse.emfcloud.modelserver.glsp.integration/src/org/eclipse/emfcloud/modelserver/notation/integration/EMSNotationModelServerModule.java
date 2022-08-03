/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.notation.integration;

import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV2;
import org.eclipse.emfcloud.modelserver.common.utils.MapBinding;
import org.eclipse.emfcloud.modelserver.common.utils.MultiBinding;
import org.eclipse.emfcloud.modelserver.edit.CommandContribution;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.ChangeBoundsCommandContribution;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.ChangeRoutingPointsCommandContribution;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.LayoutCommandContribution;
import org.eclipse.emfcloud.modelserver.integration.EMSModelServerModule;

public abstract class EMSNotationModelServerModule extends EMSModelServerModule {

   @Override
   protected void configure() {
      super.configure();
      bind(String.class).annotatedWith(NotationFileExtension.class).toInstance(getNotationFileExtension());
      bind(String.class).annotatedWith(NotationModelFormat.class).toInstance(getNotationModelFormat());
   }

   protected abstract String getNotationFileExtension();

   protected String getNotationModelFormat() { return ModelServerPathParametersV2.FORMAT_JSON_V2; }

   @Override
   protected void configureEPackages(final MultiBinding<EPackageConfiguration> binding) {
      super.configureEPackages(binding);
      binding.add(NotationPackageConfiguration.class);
   }

   @Override
   protected void configureCommandCodecs(final MapBinding<String, CommandContribution> binding) {
      super.configureCommandCodecs(binding);
      // ChangeBounds
      binding.put(ChangeBoundsCommandContribution.TYPE, ChangeBoundsCommandContribution.class);
      // ChangeRoutingPoints
      binding.put(ChangeRoutingPointsCommandContribution.TYPE, ChangeRoutingPointsCommandContribution.class);
      // Layout
      binding.put(LayoutCommandContribution.TYPE, LayoutCommandContribution.class);
   }

}
