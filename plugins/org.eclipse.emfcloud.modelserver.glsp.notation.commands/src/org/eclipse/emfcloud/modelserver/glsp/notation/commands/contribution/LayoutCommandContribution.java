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
package org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.ChangeBoundsCommand;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.ChangeRoutingPointsCommand;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.util.NotationCommandUtil;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.emf.model.notation.Shape;
import org.eclipse.glsp.server.types.ElementAndBounds;

public class LayoutCommandContribution extends NotationCommandContribution {

   public static final String TYPE = "layout";

   public static CCompoundCommand create(final Map<Shape, ElementAndBounds> changeBoundsMap,
      final Map<Edge, List<GPoint>> routingPointsMap) {
      CCompoundCommand compoundCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      compoundCommand.setType(LayoutCommandContribution.TYPE);
      changeBoundsMap.forEach((shape, elementAndBounds) -> {
         CCommand changeBoundsCommand = ChangeBoundsCommandContribution.create(
            shape.getSemanticElement().getElementId(),
            elementAndBounds.getNewPosition(), elementAndBounds.getNewSize());
         compoundCommand.getCommands().add(changeBoundsCommand);
      });
      routingPointsMap.forEach((edge, routingPoints) -> {
         CCommand changeRoutingPointsCommand = ChangeRoutingPointsCommandContribution.create(
            edge.getSemanticElement().getElementId(),
            routingPoints);
         compoundCommand.getCommands().add(changeRoutingPointsCommand);
      });
      return compoundCommand;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      if (!(command instanceof CCompoundCommand)) {
         throw new DecodingException("Origin command is expected to be an instance of CompoundCommand");
      }

      CompoundCommand layoutCommand = new CompoundCommand();
      ((CCompoundCommand) command).getCommands().forEach(childCommand -> {
         if (childCommand.getType().equals(ChangeBoundsCommandContribution.TYPE)) {
            String semanticElementId = childCommand.getProperties().get(SEMANTIC_ELEMENT_ID);
            GPoint elementPosition = getGPoint(childCommand);
            GDimension elementSize = getGDimension(childCommand);
            layoutCommand
               .append(new ChangeBoundsCommand(domain, modelUri, semanticElementId, elementPosition, elementSize));
         } else if (childCommand.getType().equals(ChangeRoutingPointsCommandContribution.TYPE)) {
            String semanticElementId = childCommand.getProperties().get(SEMANTIC_ELEMENT_ID);
            List<GPoint> newRoutingPoints = new ArrayList<>();
            ((CCompoundCommand) childCommand).getCommands().forEach(cmd -> {
               GPoint routingPoint = getGPoint(cmd);
               newRoutingPoints.add(routingPoint);
            });
            layoutCommand.append(new ChangeRoutingPointsCommand(domain, modelUri, semanticElementId, newRoutingPoints));
         }
      });
      return layoutCommand;
   }

   protected GPoint getGPoint(final CCommand command) {
      if (command.getProperties().containsKey(POSITION_X) && command.getProperties().containsKey(POSITION_Y)) {
         return NotationCommandUtil.getGPoint(command.getProperties().get(POSITION_X),
            command.getProperties().get(POSITION_Y));
      }
      return null;
   }

   protected GDimension getGDimension(final CCommand command) {
      if (command.getProperties().containsKey(WIDTH) && command.getProperties().containsKey(HEIGHT)) {
         return NotationCommandUtil.getGDimension(
            command.getProperties().get(WIDTH), command.getProperties().get(HEIGHT));
      }
      return null;
   }

}
