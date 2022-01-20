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
package org.eclipse.emfcloud.modelserver.glsp.ids;

import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelSourceLoader;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class DefaultModelSourceLoader extends EMSModelSourceLoader {

   @Inject
   @SemanticModelExtension
   String semanticModelExtension;

   @Override
   public EMSModelState createModelState(final GModelState gModelState) {
      return DefaultModelState.getModelState(gModelState);
   }

   @Override
   public EMSModelServerAccess createModelServerAccess(final String sourceURI,
      final ModelServerClient modelServerClient) {
      return new EMSNotationModelServerAccess(sourceURI, modelServerClient, semanticModelExtension, "notation");
   }

}
