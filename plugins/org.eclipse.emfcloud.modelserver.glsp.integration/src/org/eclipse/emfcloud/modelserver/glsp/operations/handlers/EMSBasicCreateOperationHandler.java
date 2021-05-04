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

import java.util.List;

import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.glsp.server.internal.util.GenericsUtil;
import org.eclipse.glsp.server.model.GModelState;
import org.eclipse.glsp.server.operations.BasicCreateOperationHandler;
import org.eclipse.glsp.server.operations.CreateOperation;

import com.google.common.collect.Lists;

@SuppressWarnings("restriction")
public abstract class EMSBasicCreateOperationHandler<T extends CreateOperation, U extends EMSModelState, V extends EMSModelServerAccess>
   extends BasicCreateOperationHandler<T> implements EMSOperationHandler<T, U, V> {

   protected final Class<U> modelStateType;
   protected final Class<V> modelServerAccessType;

   public EMSBasicCreateOperationHandler(final String... elementTypeIds) {
      super(Lists.newArrayList(elementTypeIds));
      this.modelStateType = deriveModelStateType();
      this.modelServerAccessType = deriveModelServerAccessType();
   }

   public EMSBasicCreateOperationHandler(final List<String> handledElementTypeIds) {
      super(handledElementTypeIds);
      this.modelStateType = deriveModelStateType();
      this.modelServerAccessType = deriveModelServerAccessType();
   }

   @SuppressWarnings("unchecked")
   @Override
   protected Class<T> deriveOperationType() {
      return (Class<T>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicCreateOperationHandler.class))
         .getActualTypeArguments()[0];
   }

   @SuppressWarnings("unchecked")
   protected Class<U> deriveModelStateType() {
      return (Class<U>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicCreateOperationHandler.class))
         .getActualTypeArguments()[1];
   }

   @SuppressWarnings("unchecked")
   protected Class<V> deriveModelServerAccessType() {
      return (Class<V>) (GenericsUtil.getParametrizedType(getClass(), EMSBasicCreateOperationHandler.class))
         .getActualTypeArguments()[2];
   }

   @Override
   public void executeOperation(final T operation, final GModelState gModelState) {
      if (handles(operation)) {
         EMSModelState modelState = getModelState(gModelState);
         EMSModelServerAccess modelServerAccess = getModelServerAccess(gModelState);
         executeOperation(operationType.cast(operation), modelStateType.cast(modelState),
            modelServerAccessType.cast(modelServerAccess));
      }
   }

   protected EMSModelState getModelState(final GModelState gModelState) {
      return EMSModelState.getModelState(gModelState);
   }

   protected EMSModelServerAccess getModelServerAccess(final GModelState gModelState) {
      return EMSModelState.getModelServerAccess(gModelState);
   }

}
