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
package org.eclipse.emfcloud.modelserver.glsp.actions.handlers;

import java.util.List;

import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.SaveModelAction;
import org.eclipse.glsp.server.types.GLSPServerException;

public class EMSSaveModelActionHandler
   extends EMSBasicActionHandler<SaveModelAction, EMSModelServerAccess> {

   @Override
   public List<Action> executeAction(final SaveModelAction action, final EMSModelServerAccess modelServerAccess) {

      modelServerAccess.save().thenAccept(response -> {
         if (!response.body()) {
            throw new GLSPServerException("Could not execute save action via Model Server: " + response.getMessage());
         }
      });

      return none();
   }

}
