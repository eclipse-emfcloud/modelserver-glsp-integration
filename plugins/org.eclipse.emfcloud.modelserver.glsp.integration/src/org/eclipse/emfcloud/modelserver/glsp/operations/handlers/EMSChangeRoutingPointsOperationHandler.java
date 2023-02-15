/********************************************************************************
 * Copyright (c) 2022-2023 EclipseSource and others.
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
import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.operations.ChangeRoutingPointsOperation;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public class EMSChangeRoutingPointsOperationHandler extends EMSOperationHandler<ChangeRoutingPointsOperation> {

   @Inject
   protected EMSNotationModelState modelState;
   @Inject
   protected EMSNotationModelServerAccess modelServerAccess;

   @Override
   protected void executeOperation(final ChangeRoutingPointsOperation operation) {
      Map<Edge, ElementAndRoutingPoints> changeRoutingPointsMap = new HashMap<>();
      for (ElementAndRoutingPoints element : operation.getNewRoutingPoints()) {
         modelState.getIndex().getNotation(element.getElementId(), Edge.class)
            .ifPresent(notationElement -> {
               changeRoutingPointsMap.put(notationElement, element);
            });
      }
      modelServerAccess.changeRoutingPoints(changeRoutingPointsMap).thenAccept(response -> {
         if (response.body() == null || response.body().isEmpty()) {
            throw new GLSPServerException("Could not change bounds: " + changeRoutingPointsMap.toString());
         }
      });
   }

   @Override
   public String getLabel() { return "Reroute edge"; }

}
