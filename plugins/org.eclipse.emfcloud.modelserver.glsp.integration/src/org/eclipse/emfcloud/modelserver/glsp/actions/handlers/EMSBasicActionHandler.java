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
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.internal.util.GenericsUtil;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public abstract class EMSBasicActionHandler<T extends Action, U extends EMSModelServerAccess>
   extends AbstractActionHandler<T> implements EMSActionHandler<T, U> {

   protected final Class<U> modelServerAccessType;

   @Inject
   protected GModelState gModelState;

   public EMSBasicActionHandler() {
      super();
      this.modelServerAccessType = deriveModelServerAccessType();
   }

   @SuppressWarnings("unchecked")
   @Override
   protected Class<T> deriveActionType() {
      return (Class<T>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicActionHandler.class))
         .getActualTypeArguments()[0];
   }

   @SuppressWarnings("unchecked")
   protected Class<U> deriveModelServerAccessType() {
      return (Class<U>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicActionHandler.class))
         .getActualTypeArguments()[1];
   }

   @Override
   public List<Action> executeAction(final T actualAction) {
      if (handles(actualAction)) {
         EMSModelServerAccess modelServerAccess = EMSModelState.getModelServerAccess(gModelState);
         return executeAction(actionType.cast(actualAction), modelServerAccessType.cast(modelServerAccess));
      }
      return none();
   }

   protected EMSModelState getEMSModelState() { return EMSModelState.getModelState(gModelState); }

}
