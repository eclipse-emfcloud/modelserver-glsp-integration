/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp.notation.commands;

import java.util.function.Supplier;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.server.emf.model.notation.NotationFactory;
import org.eclipse.glsp.server.emf.model.notation.SemanticElementReference;
import org.eclipse.glsp.server.emf.model.notation.Shape;

public class AddShapeCommand extends NotationElementCommand {

   protected final GPoint shapePosition;
   protected final GDimension shapeSize;
   protected String semanticElementId;
   // object's ID attribute
   protected Supplier<? extends EObject> identifiableSupplier;

   private AddShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position) {
      super(domain, modelUri);
      this.shapePosition = position;
      this.shapeSize = null;
      this.identifiableSupplier = null;
      this.semanticElementId = null;
   }

   private AddShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position,
      final GDimension size) {
      super(domain, modelUri);
      this.shapePosition = position;
      this.shapeSize = size;
      this.identifiableSupplier = null;
      this.semanticElementId = null;
   }

   public AddShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position,
      final String semanticElementId) {
      this(domain, modelUri, position);
      this.semanticElementId = semanticElementId;
   }

   public AddShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position,
      final GDimension size, final String semanticElementId) {
      this(domain, modelUri, position, size);
      this.semanticElementId = semanticElementId;
   }

   public AddShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position,
      final Supplier<? extends EObject> supplier) {
      this(domain, modelUri, position);
      this.identifiableSupplier = supplier;
   }

   public AddShapeCommand(final EditingDomain domain, final URI modelUri, final GPoint position,
      final GDimension size, final Supplier<? extends EObject> supplier) {
      this(domain, modelUri, position, size);
      this.identifiableSupplier = supplier;
   }

   @Override
   protected void doExecute() {
      Shape newShape = NotationFactory.eINSTANCE.createShape();
      newShape.setPosition(this.shapePosition);

      if (this.shapeSize != null) {
         newShape.setSize(shapeSize);
      }

      SemanticElementReference semanticReference = NotationFactory.eINSTANCE.createSemanticElementReference();
      if (this.semanticElementId != null) {
         semanticReference.setElementId(semanticElementId);
      } else {
         semanticReference.setElementId(EcoreUtil.getID(identifiableSupplier.get()));
      }
      newShape.setSemanticElement(semanticReference);

      notationDiagram.getElements().add(newShape);
   }

}
