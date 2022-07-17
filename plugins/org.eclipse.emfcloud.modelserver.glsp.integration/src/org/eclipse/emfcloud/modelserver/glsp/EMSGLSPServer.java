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

import static org.eclipse.glsp.server.types.GLSPServerException.getOrThrow;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.client.v2.ModelServerClientV2;
import org.eclipse.emfcloud.modelserver.glsp.client.ModelServerClientProvider;
import org.eclipse.glsp.server.protocol.DefaultGLSPServer;
import org.eclipse.glsp.server.protocol.DisposeClientSessionParameters;
import org.eclipse.glsp.server.protocol.InitializeResult;
import org.eclipse.glsp.server.types.GLSPServerException;
import org.eclipse.glsp.server.utils.ClientOptionsUtil;
import org.eclipse.glsp.server.utils.MapUtil;

import com.google.inject.Inject;

public class EMSGLSPServer extends DefaultGLSPServer {

   private static Logger LOGGER = LogManager.getLogger(EMSGLSPServer.class);
   private static final String TIMESTAMP_KEY = "timestamp";
   private static final String MODELSERVER_URL_KEY = "modelServerURL";

   @Inject
   protected ModelServerClientProvider modelServerClientProvider;

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
         ModelServerClientV2 client = createModelServerClient(modelServerURL);
         boolean alive = client.ping().thenApply(Response<Boolean>::body).get();
         if (alive) {
            modelServerClientProvider.setModelServerClient(client);
         }

      } catch (MalformedURLException | InterruptedException | ExecutionException e) {
         LOGGER.error("Error during initialization of modelserver connection", e);
      }

      return completableResult;
   }

   protected ModelServerClientV2 createModelServerClient(final String modelServerURL) throws MalformedURLException {
      return new ModelServerClientV2(modelServerURL);
   }

   @Override
   public CompletableFuture<Void> disposeClientSession(final DisposeClientSessionParameters params) {
      Optional<ModelServerClientV2> modelServerClient = modelServerClientProvider.get();
      if (modelServerClient.isPresent()) {
         String sourceURI = ClientOptionsUtil.getSourceUri(params.getArgs())
            .orElseThrow(() -> new GLSPServerException("No source URI given to dispose client session!"));
         modelServerClient.get().unsubscribe(sourceURI);
      }
      return super.disposeClientSession(params);
   }

}
