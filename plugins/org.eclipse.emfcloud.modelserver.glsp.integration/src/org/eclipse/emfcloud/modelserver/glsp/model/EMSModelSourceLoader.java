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
package org.eclipse.emfcloud.modelserver.glsp.model;

import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.client.ModelServerClientProvider;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.features.core.model.ModelSourceLoader;
import org.eclipse.glsp.server.features.core.model.ModelSubmissionHandler;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptions;

import com.google.inject.Inject;

public abstract class EMSModelSourceLoader implements ModelSourceLoader {

   private static Logger LOGGER = Logger.getLogger(EMSModelSourceLoader.class.getSimpleName());

   public static final String WORKSPACE_ROOT_OPTION = "workspaceRoot";

   @Inject
   private ModelServerClientProvider modelServerClientProvider;

   @Inject
   private ActionDispatcher actionDispatcher;

   @Inject
   protected ModelSubmissionHandler submissionHandler;

   @Override
   public void loadSourceModel(final RequestModelAction action, final GModelState gModelState) {
      String sourceURI = getSourceURI(action.getOptions());
      if (sourceURI.isEmpty()) {
         LOGGER.error("No source URI given to load source models");
         return;
      }
      Optional<ModelServerClient> modelServerClient = modelServerClientProvider.get();
      if (modelServerClient.isEmpty()) {
         LOGGER.error("Connection to modelserver could not be initialized");
         return;
      }

      EMSModelServerAccess modelServerAccess = createModelServerAccess(sourceURI, modelServerClient.get());

      EMSModelState modelState = createModelState(gModelState);
      modelState.initialize(action.getOptions(), modelServerAccess);

      try {
         modelState.loadSourceModels();
      } catch (GLSPServerException e) {
         LOGGER.error("Error during source model loading");
         e.printStackTrace();
         return;
      }

      modelServerAccess.subscribe(createSubscriptionListener(modelState, actionDispatcher, submissionHandler));
   }

   public abstract EMSModelServerAccess createModelServerAccess(String sourceURI,
      ModelServerClient modelServerClient);

   public abstract EMSModelState createModelState(GModelState gModelState);

   public EMSSubscriptionListener createSubscriptionListener(final EMSModelState modelState,
      final ActionDispatcher actionDispatcher, final ModelSubmissionHandler submissionHandler) {
      return new EMSSubscriptionListener(modelState, actionDispatcher, submissionHandler);
   }

   protected String getSourceURI(final Map<String, String> clientOptions) {
      String sourceURI = ClientOptions.getValue(clientOptions, ClientOptions.SOURCE_URI)
         .orElseThrow(() -> new GLSPServerException("No source URI given to load model!"));
      String workspaceRoot = ClientOptions.getValue(clientOptions, WORKSPACE_ROOT_OPTION)
         .orElseThrow(() -> new GLSPServerException("No workspace URI given to load model!"));

      return sourceURI.replace(ClientOptions.adaptUri(workspaceRoot), "").replaceFirst("/", "");
   }

}
