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
package org.eclipse.emfcloud.modelserver.glsp.operations.handlers;

import java.util.List;

import org.eclipse.glsp.server.operations.AbstractCreateOperationHandler;
import org.eclipse.glsp.server.operations.CreateOperation;

public abstract class AbstractEMSCreateOperationHandler<T extends CreateOperation>
   extends AbstractCreateOperationHandler<T> implements EMSOperationHandler<T> {

   public AbstractEMSCreateOperationHandler(final String... elementTypeIds) {
      super(elementTypeIds);
   }

   public AbstractEMSCreateOperationHandler(final List<String> handledElementTypeIds) {
      super(handledElementTypeIds);
   }

}
