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
package org.eclipse.emfcloud.modelserver.glsp.client;

import java.util.Optional;

import org.eclipse.emfcloud.modelserver.client.v2.ModelServerClientV2;

import com.google.inject.Singleton;

@Singleton
public class ModelServerClientProvider {

   private ModelServerClientV2 modelServerClient;

   public Optional<ModelServerClientV2> get() {
      return Optional.ofNullable(modelServerClient);
   }

   public void setModelServerClient(final ModelServerClientV2 modelServerClient) {
      this.modelServerClient = modelServerClient;
   }

}
