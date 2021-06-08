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
package org.eclipse.emfcloud.modelserver.glsp.notation.commands;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.modelserver.glsp.notation.Shape;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.util.NotationCommandUtil;
import org.eclipse.glsp.graph.GDimension;
import org.eclipse.glsp.graph.GPoint;

public class ChangeBoundsCommand extends NotationElementCommand {

   protected final GPoint shapePosition;
   protected final GDimension shapeSize;
   protected final Shape shape;

   public ChangeBoundsCommand(final EditingDomain domain, final URI modelUri,
      final String semanticProxyUri, final GPoint shapePosition, final GDimension shapeSize) {
      super(domain, modelUri);
      this.shapePosition = shapePosition;
      this.shapeSize = shapeSize;
      this.shape = NotationCommandUtil.getNotationElement(modelUri, domain, semanticProxyUri, Shape.class);
   }

   @Override
   protected void doExecute() {
      shape.setPosition(shapePosition);
      shape.setSize(shapeSize);
   }

}
