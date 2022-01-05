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
package org.eclipse.emfcloud.modelserver.glsp.operations.handlers;

import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.glsp.server.internal.util.GenericsUtil;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.AbstractOperationHandler;
import org.eclipse.glsp.server.operations.Operation;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public abstract class EMSBasicOperationHandler<T extends Operation, U extends EMSModelServerAccess>
   extends AbstractOperationHandler<T> implements EMSOperationHandler<T, U> {

   @Inject
   protected GModelState gModelState;

   protected final Class<U> modelServerAccessType;

   public EMSBasicOperationHandler() {
      super();
      this.modelServerAccessType = deriveModelServerAccessType();
   }

   @SuppressWarnings("unchecked")
   @Override
   protected Class<T> deriveOperationType() {
      return (Class<T>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicOperationHandler.class))
         .getActualTypeArguments()[0];
   }

   @SuppressWarnings("unchecked")
   protected Class<U> deriveModelServerAccessType() {
      return (Class<U>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicOperationHandler.class))
         .getActualTypeArguments()[1];
   }

   @Override
   public void executeOperation(final T operation) {
      if (handles(operation)) {
         EMSModelServerAccess modelServerAccess = getModelServerAccess(gModelState);
         executeOperation(operationType.cast(operation), modelServerAccessType.cast(modelServerAccess));
      }
   }

   protected EMSModelServerAccess getModelServerAccess(final GModelState gModelState) {
      return EMSModelState.getModelServerAccess(gModelState);
   }
}
