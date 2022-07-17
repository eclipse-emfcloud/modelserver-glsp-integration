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

import java.util.function.Supplier;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.integration.example.modelserver.commands.semantic.AddTaskCommand;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Identifiable;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.AddShapeCommand;
import org.eclipse.glsp.graph.GPoint;

public class AddTaskCompoundCommand extends CompoundCommand {

   public AddTaskCompoundCommand(final EditingDomain domain, final URI modelUri, final GPoint position) {

      // Chain semantic and notation command
      AddTaskCommand command = new AddTaskCommand(domain, modelUri);
      this.append(command);
      Supplier<Identifiable> taskSupplier = () -> command.getNewTask();
      this.append(new AddShapeCommand(domain, modelUri, position, taskSupplier));
   }

}
