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
package org.eclipse.emfcloud.modelserver.glsp.actions.handlers;

import java.util.List;

import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.features.core.model.RequestModelAction;
import org.eclipse.glsp.server.features.core.model.RequestModelActionHandler;

import com.google.inject.Inject;

public class EMSRequestModelActionHandler extends RequestModelActionHandler {

   @Inject
   protected EMSModelServerAccess modelServerAccess;

   @Override
   public List<Action> executeAction(final RequestModelAction action) {
      // Ensure client options are set in ModelServerAccess
      modelServerAccess.setClientOptions(action.getOptions());
      return super.executeAction(action);
   }

}
