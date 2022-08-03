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
package org.eclipse.emfcloud.modelserver.glsp;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.client.SubscriptionListener;

public interface ModelServerAccess {

   Map<String, String> getClientOptions();

   void setClientOptions(Map<String, String> options);

   EObject getSemanticModel();

   void subscribe(SubscriptionListener subscriptionListener);

   void unsubscribe();

   CompletableFuture<Response<Boolean>> save();

   CompletableFuture<Response<Boolean>> saveAll();

   CompletableFuture<Response<String>> undo();

   CompletableFuture<Response<String>> redo();

   CompletableFuture<Response<Boolean>> close();

   CompletableFuture<Response<String>> validate();

   CompletableFuture<Response<String>> getValidationConstraints();

}
