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
package org.eclipse.emfcloud.integration.example.modelserver;

import org.eclipse.emfcloud.integration.example.modelserver.contributions.AddTaskCommandContribution;
import org.eclipse.emfcloud.integration.example.modelserver.contributions.AddTransitionCommandContribution;
import org.eclipse.emfcloud.integration.example.modelserver.contributions.RemoveTaskCommandContribution;
import org.eclipse.emfcloud.integration.example.modelserver.contributions.RemoveTransitionCommandContribution;
import org.eclipse.emfcloud.modelserver.common.utils.MapBinding;
import org.eclipse.emfcloud.modelserver.common.utils.MultiBinding;
import org.eclipse.emfcloud.modelserver.edit.CommandContribution;
import org.eclipse.emfcloud.modelserver.emf.common.ModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.common.ResourceSetFactory;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.notation.integration.EMSNotationModelServerModule;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationResource;

public class TaskListModelServerModule extends EMSNotationModelServerModule {

   @Override
   protected void configureEPackages(final MultiBinding<EPackageConfiguration> binding) {
      super.configureEPackages(binding);
      binding.add(TaskListPackageConfiguration.class);
   }

   @Override
   protected Class<? extends ModelResourceManager> bindModelResourceManager() {
      return TaskListModelResourceManager.class;
   }

   @Override
   protected Class<? extends ResourceSetFactory> bindResourceSetFactory() {
      return TaskListResourceSetFactory.class;
   }

   @Override
   protected void configureCommandCodecs(final MapBinding<String, CommandContribution> binding) {
      super.configureCommandCodecs(binding);
      binding.put(AddTaskCommandContribution.TYPE, AddTaskCommandContribution.class);
      binding.put(AddTransitionCommandContribution.TYPE, AddTransitionCommandContribution.class);
      binding.put(RemoveTaskCommandContribution.TYPE, RemoveTaskCommandContribution.class);
      binding.put(RemoveTransitionCommandContribution.TYPE, RemoveTransitionCommandContribution.class);
   }

   @Override
   protected String getSemanticFileExtension() { return TaskListResource.FILE_EXTENSION; }

   @Override
   protected String getNotationFileExtension() { return NotationResource.FILE_EXTENSION; }

}
