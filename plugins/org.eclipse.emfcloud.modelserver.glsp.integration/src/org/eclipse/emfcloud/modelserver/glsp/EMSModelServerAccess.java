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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.client.ModelServerClientApi;
import org.eclipse.emfcloud.modelserver.client.NotificationSubscriptionListener;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.glsp.server.protocol.GLSPServerException;

import com.google.common.base.Preconditions;

public class EMSModelServerAccess {

   private static Logger LOGGER = Logger.getLogger(EMSModelServerAccess.class);

   protected static final String FORMAT_XMI = "xmi";

   protected final URI baseSourceUri;
   protected String semanticFileExtension;

   protected final ModelServerClient modelServerClient;
   protected NotificationSubscriptionListener<EObject> subscriptionListener;

   public EMSModelServerAccess(final String sourceURI, final ModelServerClient modelServerClient,
      final String semanticFileExtension) {
      Preconditions.checkNotNull(modelServerClient);
      this.baseSourceUri = URI.createURI(sourceURI, true).trimFileExtension();
      this.modelServerClient = modelServerClient;
      this.semanticFileExtension = semanticFileExtension;
   }

   public String getSemanticURI() { return baseSourceUri.appendFileExtension(this.semanticFileExtension).toString(); }

   public ModelServerClientApi<EObject> getModelServerClient() { return modelServerClient; }

   public EObject getSemanticModel() {
      try {
         return modelServerClient.get(getSemanticURI(), FORMAT_XMI).thenApply(res -> res.body()).get();
      } catch (InterruptedException | ExecutionException e) {
         LOGGER.error(e);
         throw new GLSPServerException("Error during model loading", e);
      }
   }

   public void subscribe(final NotificationSubscriptionListener<EObject> subscriptionListener) {
      this.subscriptionListener = subscriptionListener;
      this.modelServerClient.subscribe(getSemanticURI(), subscriptionListener, FORMAT_XMI);
   }

   public void unsubscribe() {
      if (subscriptionListener != null) {
         this.modelServerClient.unsubscribe(getSemanticURI());
      }
   }

   protected CompletableFuture<Response<Boolean>> edit(final CCommand command) {
      return this.modelServerClient.edit(getSemanticURI(), command, FORMAT_XMI);
   }

   public CompletableFuture<Response<Boolean>> save() {
      return this.modelServerClient.save(getSemanticURI());
   }

   public CompletableFuture<Response<Boolean>> undo() {
      return this.modelServerClient.undo(getSemanticURI());
   }

   public CompletableFuture<Response<Boolean>> redo() {
      return this.modelServerClient.redo(getSemanticURI());
   }

}
