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
import org.eclipse.emfcloud.modelserver.glsp.actions.EMSRefreshModelAction;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.features.core.model.ModelSubmissionHandler;

import com.google.inject.Inject;

/**
 * Handles model updates with an ActionHandler, to make sure we're in a thread-safe context.
 */
public class EMSRefreshModelActionHandler
   extends EMSBasicActionHandler<EMSRefreshModelAction, EMSModelServerAccess> {

   @Inject
   protected ModelSubmissionHandler submissionHandler;

   @Override
   public List<Action> executeAction(final EMSRefreshModelAction action,
      final EMSModelServerAccess modelServerAccess) {
      // reload models
      getEMSModelState().loadSourceModels();
      // refresh GModelRoot
      return submissionHandler.submitModel();
   }

}
