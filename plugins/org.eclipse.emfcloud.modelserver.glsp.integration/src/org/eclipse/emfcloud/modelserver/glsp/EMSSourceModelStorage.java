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
package org.eclipse.emfcloud.modelserver.glsp;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.SubscriptionListener;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.SaveModelAction;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;
import org.eclipse.glsp.server.features.core.model.SourceModelStorage;

import com.google.inject.Inject;

public class EMSSourceModelStorage implements SourceModelStorage {

   @Inject
   protected ActionDispatcher actionDispatcher;
   @Inject
   protected EMSModelState modelState;
   @Inject
   protected EMSModelServerAccess modelServerAccess;

   @Override
   public void loadSourceModel(final RequestModelAction action) {
      doLoadSourceModel();
      doSubscribe();
   }

   public void loadSourceModel() {
      doLoadSourceModel();
   }

   protected void doLoadSourceModel() {
      EObject semanticRoot = modelServerAccess.getSemanticModel();
      this.modelState.setSemanticModel(semanticRoot);
   }

   protected void doSubscribe() {
      modelServerAccess.subscribe(createSubscriptionListener(modelServerAccess.getSemanticURI(), actionDispatcher));
   }

   protected SubscriptionListener createSubscriptionListener(final String modelUri,
      final ActionDispatcher actionDispatcher) {
      return new EMSSubscriptionListener(modelUri, actionDispatcher);
   }

   @Override
   public void saveSourceModel(final SaveModelAction action) {
      modelServerAccess.save();
   }

}
