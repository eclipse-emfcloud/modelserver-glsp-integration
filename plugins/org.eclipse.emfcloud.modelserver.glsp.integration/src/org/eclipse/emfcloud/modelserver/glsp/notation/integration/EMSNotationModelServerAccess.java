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
package org.eclipse.emfcloud.modelserver.glsp.notation.integration;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.client.Response;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.notation.Shape;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.ChangeBoundsCommandContribution;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.server.protocol.GLSPServerException;
import org.eclipse.glsp.server.types.ElementAndBounds;

public abstract class EMSNotationModelServerAccess extends EMSModelServerAccess {

   protected String notationFileExtension;

   private static Logger LOGGER = Logger.getLogger(EMSNotationModelServerAccess.class);

   public EMSNotationModelServerAccess(final String sourceURI, final ModelServerClient modelServerClient,
      final String semanticFileExtension, final String notationFileExtension) {
      super(sourceURI, modelServerClient, semanticFileExtension);
      this.notationFileExtension = notationFileExtension;
   }

   public String getNotationURI() { return baseSourceUri.appendFileExtension(this.notationFileExtension).toString(); }

   public EObject getNotationModel() {
      try {
         return modelServerClient.get(getNotationURI(), FORMAT_XMI).thenApply(res -> res.body()).get();
      } catch (InterruptedException | ExecutionException e) {
         LOGGER.error(e);
         throw new GLSPServerException("Error during model loading", e);
      }
   }

   /*
    * Change Bounds
    */
   public CompletableFuture<Response<Boolean>> changeBounds(final Map<Shape, ElementAndBounds> changeBoundsMap) {
      CCompoundCommand compoundCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      compoundCommand.setType(ChangeBoundsCommandContribution.TYPE);
      changeBoundsMap.forEach((shape, elementAndBounds) -> {
         CCommand changeBoundsCommand = ChangeBoundsCommandContribution.create(shape.getSemanticElement().getUri(),
            elementAndBounds.getNewPosition(), elementAndBounds.getNewSize());
         compoundCommand.getCommands().add(changeBoundsCommand);
      });
      return this.edit(compoundCommand);
   }

   /*
    * Auto layouting
    */
   protected CCompoundCommand createLayoutCommand(final EMSNotationModelState modelState,
      final GModelElement layoutedRoot) {
      CCompoundCommand compoundCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      compoundCommand.setType(ChangeBoundsCommandContribution.TYPE);

      layoutedRoot.getChildren().forEach(gModelElement -> {
         if (gModelElement instanceof GNode) {
            modelState.getIndex().getNotation(gModelElement.getId(), Shape.class).ifPresent(shape -> {
               CCommand changeBoundsCommand = ChangeBoundsCommandContribution.create(
                  shape.getSemanticElement().getUri(), ((GNode) gModelElement).getPosition(),
                  ((GNode) gModelElement).getSize());
               compoundCommand.getCommands().add(changeBoundsCommand);
            });
         }
      });

      return compoundCommand;
   }

   public CompletableFuture<Response<Boolean>> setLayout(final EMSNotationModelState modelState,
      final GModelElement layoutedRoot) {
      CCompoundCommand compoundCommand = createLayoutCommand(modelState, layoutedRoot);
      return this.edit(compoundCommand);
   }
}
