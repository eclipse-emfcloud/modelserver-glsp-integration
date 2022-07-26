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
package org.eclipse.emfcloud.modelserver.glsp.notation.integration;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.glsp.EMSSourceModelStorage;
import org.eclipse.glsp.server.emf.model.notation.Diagram;

import com.google.inject.Inject;

public class EMSNotationSourceModelStorage extends EMSSourceModelStorage {

   @Inject
   protected EMSNotationModelState modelState;

   @Inject
   protected EMSNotationModelServerAccess modelServerAccess;

   @Override
   protected void doLoadSourceModel() {
      EObject semanticRoot = modelServerAccess.getSemanticModel();
      this.modelState.setSemanticModel(semanticRoot);
      Diagram notationRoot = modelServerAccess.getNotationModel();
      this.modelState.setNotationModel(notationRoot);
   }

}
