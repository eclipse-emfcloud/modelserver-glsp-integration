/********************************************************************************
 * Copyright (c) 2021-2023 EclipseSource and others.
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
import org.eclipse.emfcloud.modelserver.glsp.EMSModelState;
import org.eclipse.glsp.server.operations.CreateOperation;
import org.eclipse.glsp.server.operations.CreateOperationHandler;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

public abstract class EMSCreateOperationHandler<T extends CreateOperation>
   extends EMSOperationHandler<T> implements CreateOperationHandler<T> {

   @Inject
   protected EMSModelState modelState;
   @Inject
   protected EMSModelServerAccess modelServerAccess;

   protected List<String> handledElementTypeIds;

   public EMSCreateOperationHandler(final String... elementTypeIds) {
      this(Lists.newArrayList(elementTypeIds));
   }

   public EMSCreateOperationHandler(final List<String> handledElementTypeIds) {
      this.handledElementTypeIds = handledElementTypeIds;
   }

   @Override
   public List<String> getHandledElementTypeIds() { return handledElementTypeIds; }

   public void setHandledElementTypeIds(final List<String> handledElementTypeIds) {
      this.handledElementTypeIds = handledElementTypeIds;
   }

}
