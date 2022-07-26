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
package org.eclipse.emfcloud.modelserver.glsp.notation.commands;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.util.NotationCommandUtil;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.emf.model.notation.Edge;

public class ChangeRoutingPointsCommand extends NotationElementCommand {

   protected final Edge edge;
   protected final List<GPoint> newRoutingPoints;

   public ChangeRoutingPointsCommand(final EditingDomain domain, final URI modelUri,
      final String semanticElementId, final List<GPoint> newRoutingPoints) {
      super(domain, modelUri);
      this.newRoutingPoints = newRoutingPoints;
      this.edge = NotationCommandUtil.getNotationElement(modelUri, domain, semanticElementId, Edge.class);
   }

   @Override
   protected void doExecute() {
      edge.getBendPoints().clear();
      edge.getBendPoints().addAll(newRoutingPoints);
   }

}
