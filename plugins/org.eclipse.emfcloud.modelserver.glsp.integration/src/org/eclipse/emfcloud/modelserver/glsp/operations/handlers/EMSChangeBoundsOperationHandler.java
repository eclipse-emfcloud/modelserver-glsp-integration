/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp.operations.handlers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationModelState;
import org.eclipse.glsp.server.emf.model.notation.Shape;
import org.eclipse.glsp.server.operations.ChangeBoundsOperation;
import org.eclipse.glsp.server.types.ElementAndBounds;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public class EMSChangeBoundsOperationHandler extends EMSOperationHandler<ChangeBoundsOperation> {

   @Inject
   protected EMSNotationModelState modelState;
   @Inject
   protected EMSNotationModelServerAccess modelServerAccess;

   @Override
   public void executeOperation(final ChangeBoundsOperation operation) {

      Map<Shape, ElementAndBounds> changeBoundsMap = new HashMap<>();
      for (ElementAndBounds element : operation.getNewBounds()) {
         modelState.getIndex().getNotation(element.getElementId(), Shape.class)
            .ifPresent(shape -> {
               changeBoundsMap.put(shape, element);
            });
      }
      modelServerAccess.changeBounds(changeBoundsMap).thenAccept(response -> {
         if (response.body() == null || response.body().isEmpty()) {
            throw new GLSPServerException("Could not change bounds: " + changeBoundsMap.toString());
         }
      });
   }

   @Override
   public String getLabel() { return "Change bounds"; }

}
