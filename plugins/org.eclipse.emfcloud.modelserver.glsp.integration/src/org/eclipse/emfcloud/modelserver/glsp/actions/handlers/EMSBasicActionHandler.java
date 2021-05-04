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
import org.eclipse.glsp.server.actions.Action;
import org.eclipse.glsp.server.actions.BasicActionHandler;
import org.eclipse.glsp.server.internal.util.GenericsUtil;
import org.eclipse.glsp.server.model.GModelState;

@SuppressWarnings("restriction")
public abstract class EMSBasicActionHandler<T extends Action, U extends EMSModelState, V extends EMSModelServerAccess>
   extends BasicActionHandler<T> implements EMSActionHandler<T, U, V> {

   protected final Class<U> modelStateType;
   protected final Class<V> modelServerAccessType;

   public EMSBasicActionHandler() {
      super();
      this.modelStateType = deriveModelStateType();
      this.modelServerAccessType = deriveModelServerAccessType();
   }

   @SuppressWarnings("unchecked")
   @Override
   protected Class<T> deriveActionType() {
      return (Class<T>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicActionHandler.class))
         .getActualTypeArguments()[0];
   }

   @SuppressWarnings("unchecked")
   protected Class<U> deriveModelStateType() {
      return (Class<U>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicActionHandler.class))
         .getActualTypeArguments()[1];
   }

   @SuppressWarnings("unchecked")
   protected Class<V> deriveModelServerAccessType() {
      return (Class<V>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicActionHandler.class))
         .getActualTypeArguments()[2];
   }

   @Override
   public List<Action> executeAction(final T actualAction, final GModelState gModelState) {
      if (handles(actualAction)) {
         EMSModelState modelState = EMSModelState.getModelState(gModelState);
         EMSModelServerAccess modelServerAccess = EMSModelState.getModelServerAccess(modelState);
         return executeAction(actionType.cast(actualAction), modelStateType.cast(modelState),
            modelServerAccessType.cast(modelServerAccess));
      }
      return none();
   }

}
