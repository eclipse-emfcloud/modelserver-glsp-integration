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
package org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution;

import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.glsp.notation.Shape;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.ChangeBoundsCommand;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.util.NotationCommandUtil;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.types.ElementAndBounds;

public class ChangeBoundsCommandContribution extends NotationCommandContribution {

   public static final String TYPE = "changeBounds";

   protected static CCommand create(final String semanticUri) {
      CCommand changeBoundsCommand = CCommandFactory.eINSTANCE.createCommand();
      changeBoundsCommand.setType(TYPE);
      changeBoundsCommand.getProperties().put(SEMANTIC_PROXI_URI, semanticUri);
      return changeBoundsCommand;
   }

   public static CCommand create(final String semanticUri, final GPoint position, final GDimension size) {
      CCommand changeBoundsCommand = create(semanticUri);
      changeBoundsCommand.getProperties().put(POSITION_X, String.valueOf(position.getX()));
      changeBoundsCommand.getProperties().put(POSITION_Y, String.valueOf(position.getY()));
      changeBoundsCommand.getProperties().put(HEIGHT, String.valueOf(size.getHeight()));
      changeBoundsCommand.getProperties().put(WIDTH, String.valueOf(size.getWidth()));
      return changeBoundsCommand;
   }

   public static CCommand create(final String semanticUri, final GPoint position) {
      CCommand changeBoundsCommand = create(semanticUri);
      changeBoundsCommand.getProperties().put(POSITION_X, String.valueOf(position.getX()));
      changeBoundsCommand.getProperties().put(POSITION_Y, String.valueOf(position.getY()));
      return changeBoundsCommand;
   }

   public static CCommand create(final String semanticUri, final GDimension size) {
      CCommand changeBoundsCommand = create(semanticUri);
      changeBoundsCommand.getProperties().put(HEIGHT, String.valueOf(size.getHeight()));
      changeBoundsCommand.getProperties().put(WIDTH, String.valueOf(size.getWidth()));
      return changeBoundsCommand;
   }

   public static CCompoundCommand create(final Map<Shape, ElementAndBounds> changeBoundsMap) {
      CCompoundCommand compoundCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      compoundCommand.setType(ChangeBoundsCommandContribution.TYPE);
      changeBoundsMap.forEach((shape, elementAndBounds) -> {
         CCommand changeBoundsCommand = ChangeBoundsCommandContribution.create(shape.getSemanticElement().getUri(),
            elementAndBounds.getNewPosition(), elementAndBounds.getNewSize());
         compoundCommand.getCommands().add(changeBoundsCommand);
      });
      return compoundCommand;
   }

   @Override
   protected Command toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      if (command instanceof CCompoundCommand) {
         CompoundCommand changeBoundsCommand = new CompoundCommand();

         ((CCompoundCommand) command).getCommands().forEach(childCommand -> {
            String semanticProxyUri = childCommand.getProperties().get(SEMANTIC_PROXI_URI);
            GPoint elementPosition = getElementPosition(childCommand);
            GDimension elementSize = getElementSize(childCommand);
            changeBoundsCommand
               .append(new ChangeBoundsCommand(domain, modelUri, semanticProxyUri, elementPosition, elementSize));
         });
         return changeBoundsCommand;
      }

      String semanticProxyUri = command.getProperties().get(SEMANTIC_PROXI_URI);
      GPoint elementPosition = getElementPosition(command);
      GDimension elementSize = getElementSize(command);
      return new ChangeBoundsCommand(domain, modelUri, semanticProxyUri, elementPosition, elementSize);
   }

   protected GPoint getElementPosition(final CCommand command) {
      if (command.getProperties().containsKey(POSITION_X) && command.getProperties().containsKey(POSITION_Y)) {
         return NotationCommandUtil.getGPoint(command.getProperties().get(POSITION_X),
            command.getProperties().get(POSITION_Y));
      }
      return null;
   }

   protected GDimension getElementSize(final CCommand command) {
      if (command.getProperties().containsKey(WIDTH) && command.getProperties().containsKey(HEIGHT)) {
         return NotationCommandUtil.getGDimension(
            command.getProperties().get(WIDTH), command.getProperties().get(HEIGHT));
      }
      return null;
   }

}
