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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emfcloud.modelserver.edit.command.BasicCommandContribution;

public abstract class NotationCommandContribution extends BasicCommandContribution<Command> {

   public static final String SEMANTIC_ELEMENT_ID = "semanticElementId";
   public static final String POSITION_X = "positionX";
   public static final String POSITION_Y = "positionY";
   public static final String HEIGHT = "height";
   public static final String WIDTH = "width";

}
