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

import java.net.MalformedURLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.glsp.client.ModelServerClientProvider;
import org.eclipse.glsp.server.jsonrpc.DefaultGLSPServer;

import com.google.inject.Inject;

public class EMSGLSPServer extends DefaultGLSPServer<EMSGLSPServerInitializeOptions> {

   private static Logger LOGGER = Logger.getLogger(EMSGLSPServer.class.getSimpleName());

   @Inject
   protected ModelServerClientProvider modelServerClientProvider;

   public EMSGLSPServer() {
      super(EMSGLSPServerInitializeOptions.class);
   }

   @Override
   public CompletableFuture<Boolean> handleOptions(final EMSGLSPServerInitializeOptions options) {
      if (options != null) {
         LOGGER.debug(String.format("[%s] Pinging modelserver", options.getTimestamp()));

         try {
            ModelServerClient client = createModelServerClient(options.getModelServerURL());
            boolean alive = client.ping().thenApply(Response<Boolean>::body).get();
            if (alive) {
               modelServerClientProvider.setModelServerClient(client);
               return CompletableFuture.completedFuture(true);
            }

         } catch (MalformedURLException | InterruptedException | ExecutionException e) {
            LOGGER.error("Error during initialization of modelserver connection", e);
         }
      }

      return CompletableFuture.completedFuture(true);
   }

   protected ModelServerClient createModelServerClient(final String modelServerURL) throws MalformedURLException {
      return new ModelServerClient(modelServerURL);
   }

}
