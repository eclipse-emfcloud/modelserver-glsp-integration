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

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.client.SubscriptionListener;
import org.eclipse.emfcloud.modelserver.client.v2.ModelServerClientV2;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.glsp.client.ModelServerClientProvider;
import org.eclipse.emfcloud.modelserver.integration.SemanticFileExtension;
import org.eclipse.emfcloud.modelserver.integration.SemanticModelFormat;
import org.eclipse.glsp.server.emf.EMFIdGenerator;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;

import com.google.inject.Inject;

public class EMSModelServerAccess implements ModelServerAccess {

   private static Logger LOGGER = LogManager.getLogger(EMSModelServerAccess.class);

   @Inject
   @SemanticFileExtension
   protected String semanticFileExtension;
   @Inject
   @SemanticModelFormat
   protected String semanticFormat;
   @Inject
   protected ModelServerClientProvider modelServerClientProvider;
   @Inject
   protected EMFIdGenerator idGenerator;

   protected Map<String, String> clientOptions;
   protected URI baseSourceUri;
   protected ModelServerClientV2 modelServerClient;
   protected SubscriptionListener subscriptionListener;

   protected ModelServerClientV2 getModelServerClient() {
      if (modelServerClient == null) {
         modelServerClient = modelServerClientProvider.get().orElseThrow();
      }
      return modelServerClient;
   }

   @Override
   public Map<String, String> getClientOptions() { return clientOptions; }

   @Override
   public void setClientOptions(final Map<String, String> clientOptions) {
      this.clientOptions = clientOptions;
      String sourceURI = ClientOptionsUtil.getSourceUri(clientOptions)
         .orElseThrow(() -> new GLSPServerException("No source URI given to load model!"));
      this.baseSourceUri = URI.createURI(sourceURI, true).trimFileExtension();
   }

   protected String getSemanticURI() {
      return baseSourceUri.appendFileExtension(this.semanticFileExtension).toString();
   }

   @Override
   public EObject getSemanticModel() {
      try {
         return getModelServerClient().get(getSemanticURI(), semanticFormat).thenApply(res -> res.body()).get();
      } catch (InterruptedException | ExecutionException e) {
         LOGGER.error(e);
         throw new GLSPServerException("Error during model loading", e);
      }
   }

   @Override
   public void subscribe(final SubscriptionListener subscriptionListener) {
      this.subscriptionListener = subscriptionListener;
      getModelServerClient().subscribe(getSemanticURI(), subscriptionListener);
   }

   @Override
   public void unsubscribe() {
      if (subscriptionListener != null) {
         getModelServerClient().unsubscribe(getSemanticURI());
         subscriptionListener = null;
      }
   }

   @Override
   public CompletableFuture<Response<Boolean>> save() {
      return getModelServerClient().save(getSemanticURI());
   }

   @Override
   public CompletableFuture<Response<Boolean>> saveAll() {
      return getModelServerClient().saveAll();
   }

   @Override
   public CompletableFuture<Response<String>> undo() {
      return getModelServerClient().undo(getSemanticURI());
   }

   @Override
   public CompletableFuture<Response<String>> redo() {
      return getModelServerClient().redo(getSemanticURI());
   }

   @Override
   public CompletableFuture<Response<Boolean>> close() {
      return getModelServerClient().close(getSemanticURI());
   }

   @Override
   public CompletableFuture<Response<String>> validate() {
      return getModelServerClient().validate(getSemanticURI());
   }

   @Override
   public CompletableFuture<Response<String>> getValidationConstraints() {
      return getModelServerClient().getValidationConstraints(getSemanticURI());
   }

   protected CompletableFuture<Response<String>> edit(final CCommand command) {
      return getModelServerClient().edit(getSemanticURI(), command, semanticFormat);
   }

}
