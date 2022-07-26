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
import org.eclipse.emfcloud.integration.example.modelserver.commands.compound.AddTransitionCompoundCommand;
import org.eclipse.emfcloud.modelserver.command.CCommand;
import org.eclipse.emfcloud.modelserver.command.CCommandFactory;
import org.eclipse.emfcloud.modelserver.command.CCompoundCommand;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.edit.CommandCodec;
import org.eclipse.emfcloud.modelserver.edit.command.CompoundCommandContribution;

import com.google.inject.Inject;

public class AddTransitionCommandContribution extends CompoundCommandContribution {

   @Inject
   public AddTransitionCommandContribution(final CommandCodec commandCodec) {
      super(commandCodec);
   }

   public static final String TYPE = "addTransition";
   public static final String SOURCE_TASK_ELEMENT_ID = "sourceElementId";
   public static final String TARGET_TASK_ELEMENT_ID = "targetElementId";

   public static CCompoundCommand create(final String sourceId, final String targetId) {
      CCompoundCommand addCommand = CCommandFactory.eINSTANCE.createCompoundCommand();
      addCommand.setType(TYPE);
      addCommand.getProperties().put(SOURCE_TASK_ELEMENT_ID, sourceId);
      addCommand.getProperties().put(TARGET_TASK_ELEMENT_ID, targetId);
      return addCommand;
   }

   @Override
   protected CompoundCommand toServer(final URI modelUri, final EditingDomain domain, final CCommand command)
      throws DecodingException {

      String sourceElementId = command.getProperties().get(SOURCE_TASK_ELEMENT_ID);
      String targetElementId = command.getProperties().get(TARGET_TASK_ELEMENT_ID);

      return new AddTransitionCompoundCommand(domain, modelUri, sourceElementId, targetElementId);
   }

}
