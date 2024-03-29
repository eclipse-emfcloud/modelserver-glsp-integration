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
package org.eclipse.emfcloud.integration.example.modelserver.contributions;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.integration.example.modelserver.commands.compound.RemoveTaskCompoundCommand;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.EMSCompoundCommandContribution;

public class RemoveTaskCommandContribution extends EMSCompoundCommandContribution {

   public static final String TYPE = "removeTask";

   public static CCompoundCommand create(final String semanticElementId) {
      CCompoundCommand removeTaskCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      removeTaskCommand.setType(TYPE);
      removeTaskCommand.getProperties().put(SEMANTIC_ELEMENT_ID, semanticElementId);
      return removeTaskCommand;
   }

   @Override
   protected CompoundCommand toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      String semanticElementId = command.getProperties().get(SEMANTIC_ELEMENT_ID);
      return new RemoveTaskCompoundCommand(domain, modelUri, semanticElementId);
   }

}
