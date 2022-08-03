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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.ChangeBoundsCommandContribution;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.ChangeRoutingPointsCommandContribution;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.LayoutCommandContribution;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationFileExtension;
import org.eclipse.emfcloud.modelserver.notation.integration.NotationModelFormat;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.emf.model.notation.Diagram;
import org.eclipse.glsp.server.emf.model.notation.Edge;
import org.eclipse.glsp.server.emf.model.notation.Shape;
import org.eclipse.glsp.server.types.ElementAndBounds;
import org.eclipse.glsp.server.types.ElementAndRoutingPoints;
import org.eclipse.glsp.server.types.GLSPServerException;

import com.google.inject.Inject;

public class EMSNotationModelServerAccess extends EMSModelServerAccess {

   private static Logger LOGGER = LogManager.getLogger(EMSNotationModelServerAccess.class);

   @Inject
   @NotationFileExtension
   protected String notationFileExtension;
   @Inject
   @NotationModelFormat
   protected String notationFormat;

   public String getNotationURI() { return baseSourceUri.appendFileExtension(this.notationFileExtension).toString(); }

   public Diagram getNotationModel() {
      try {
         EObject notationModel = getModelServerClient().get(getNotationURI(), notationFormat)
            .thenApply(res -> res.body()).get();
         if (notationModel instanceof Diagram) {
            return (Diagram) notationModel;
         }
         throw new GLSPServerException("Error during model loading, notation model is not a Diagram!");
      } catch (InterruptedException | ExecutionException e) {
         LOGGER.error(e);
         throw new GLSPServerException("Error during model loading", e);
      }
   }

   /*
    * Change Bounds
    */
   public CompletableFuture<Response<String>> changeBounds(final Map<Shape, ElementAndBounds> changeBoundsMap) {
      CCompoundCommand compoundCommand = ChangeBoundsCommandContribution.create(changeBoundsMap);
      return this.edit(compoundCommand);
   }

   public CompletableFuture<Response<String>> changeBounds(final Shape shape, final ElementAndBounds changedBounds) {
      CCommand changeBoundsCommand = ChangeBoundsCommandContribution.create(shape.getSemanticElement().getElementId(),
         changedBounds.getNewPosition(), changedBounds.getNewSize());
      return this.edit(changeBoundsCommand);
   }

   public CompletableFuture<Response<String>> changePosition(final Shape shape, final GPoint position) {
      CCommand changePositionCommand = ChangeBoundsCommandContribution.create(shape.getSemanticElement().getElementId(),
         position);
      return this.edit(changePositionCommand);
   }

   public CompletableFuture<Response<String>> changeSize(final Shape shape, final GDimension size) {
      CCommand changeSizeCommand = ChangeBoundsCommandContribution.create(shape.getSemanticElement().getElementId(),
         size);
      return this.edit(changeSizeCommand);
   }

   /*
    * Change Routing Points
    */
   public CompletableFuture<Response<String>> changeRoutingPoints(
      final Map<Edge, ElementAndRoutingPoints> changeBendPointsMap) {
      CCompoundCommand compoundCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      compoundCommand.setType(ChangeRoutingPointsCommandContribution.TYPE);

      changeBendPointsMap.forEach((edge, elementAndRoutingPoints) -> {
         CCommand changeRoutingPointsCommand = ChangeRoutingPointsCommandContribution
            .create(edge.getSemanticElement().getElementId(), elementAndRoutingPoints.getNewRoutingPoints());
         compoundCommand.getCommands().add(changeRoutingPointsCommand);
      });
      return this.edit(compoundCommand);
   }

   /*
    * Auto layouting
    */
   public CompletableFuture<Response<String>> setLayout(final EMSNotationModelState modelState,
      final GModelElement layoutedRoot) {
      Map<Shape, ElementAndBounds> changeBoundsMap = new HashMap<>();
      Map<Edge, List<GPoint>> changeBendPointsMap = new HashMap<>();

      layoutedRoot.getChildren().forEach(layoutedGModelElement -> {
         if (layoutedGModelElement instanceof GNode) {
            modelState.getIndex().getNotation(layoutedGModelElement.getId(), Shape.class).ifPresent(shape -> {
               ElementAndBounds eb = new ElementAndBounds();
               eb.setNewPosition(((GNode) layoutedGModelElement).getPosition());
               eb.setNewSize(((GNode) layoutedGModelElement).getSize());
               changeBoundsMap.put(shape, eb);
            });
         } else if (layoutedGModelElement instanceof GEdge) {
            modelState.getIndex().getNotation(layoutedGModelElement.getId(), Edge.class).ifPresent(edge -> {
               changeBendPointsMap.put(edge, ((GEdge) layoutedGModelElement).getRoutingPoints());
            });
         }
      });

      CCompoundCommand compoundCommand = LayoutCommandContribution.create(changeBoundsMap, changeBendPointsMap);
      return this.edit(compoundCommand);
   }
}
