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

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.glsp.server.protocol.DefaultGLSPServer;
import org.eclipse.glsp.server.protocol.DisposeClientSessionParameters;
import org.eclipse.glsp.server.protocol.InitializeResult;
import org.eclipse.glsp.server.utils.MapUtil;

public class EMSGLSPServer extends DefaultGLSPServer {

   private static Logger LOGGER = Logger.getLogger(EMSGLSPServer.class.getSimpleName());
   private static final String TIMESTAMP_KEY = "timestamp";
   private static final String MODELSERVER_URL_KEY = "modelServerURL";
   private static final String MODEL_URI_KEY = "modelUri";

   protected String modelServerUrl;

   // @Inject
   // protected ModelServerClientProvider modelServerClientProvider;

   public EMSGLSPServer() {
      super();
      this.modelServerUrl = "http://localhost:8081/api/v1/";
   }

   @Override
   protected CompletableFuture<InitializeResult> handleIntializeArgs(final InitializeResult result,
      final Map<String, String> args) {
      CompletableFuture<InitializeResult> completableResult = CompletableFuture.completedFuture(result);
      if (args.isEmpty()) {
         return completableResult;
      }

      String timestamp = getOrThrow(MapUtil.getValue(args, TIMESTAMP_KEY),
         "No value present for the given key: " + TIMESTAMP_KEY);
      String modelServerURL = getOrThrow(MapUtil.getValue(args, MODELSERVER_URL_KEY),
         "No value present for the given key: " + MODELSERVER_URL_KEY);

      LOGGER.debug(String.format("[%s] Pinging modelserver", timestamp));

      try {
         ModelServerClient client = createModelServerClient(modelServerURL);
         boolean alive = client.ping().thenApply(Response<Boolean>::body).get();
         if (alive) {
            // modelServerClientProvider.setModelServerClient(client);
         }

      } catch (MalformedURLException | InterruptedException | ExecutionException e) {
         LOGGER.error("Error during initialization of modelserver connection", e);
      }

      return completableResult;
   }

   protected ModelServerClient createModelServerClient(final String modelServerURL) throws MalformedURLException {
      return new ModelServerClient(modelServerURL);
   }

   @Override
   public CompletableFuture<Void> disposeClientSession(final DisposeClientSessionParameters params) {
      // Optional<ModelServerClient> modelServerClient = modelServerClientProvider.get();
      try {
         ModelServerClient modelServerClient = createModelServerClient(this.modelServerUrl);
         Optional<String> modelUri = MapUtil.getValue(params.getArgs(), MODEL_URI_KEY);
         if (modelUri.isPresent()) {
            modelServerClient.unsubscribe(modelUri.get());
         }
      } catch (MalformedURLException e) {
         LOGGER.error("Error during disposeClientSession", e);
      }
      return super.disposeClientSession(params);
   }

}
