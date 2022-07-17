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
package org.eclipse.emfcloud.integration.example.modelserver.commands.compound;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.integration.example.modelserver.commands.semantic.RemoveTransitionCommand;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.RemoveNotationElementCommand;

public class RemoveTransitionCompoundCommand extends CompoundCommand {

   public RemoveTransitionCompoundCommand(final EditingDomain domain, final URI modelUri,
      final String semanticElementId) {

      // Remove semantic and notation element
      this.append(new RemoveTransitionCommand(domain, modelUri, semanticElementId));
      this.append(new RemoveNotationElementCommand(domain, modelUri, semanticElementId));
   }

}
