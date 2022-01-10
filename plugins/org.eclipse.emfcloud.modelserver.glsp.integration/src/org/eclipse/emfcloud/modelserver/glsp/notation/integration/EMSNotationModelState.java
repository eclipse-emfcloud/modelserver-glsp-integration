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
package org.eclipse.emfcloud.modelserver.glsp.notation.integration;

import org.eclipse.emfcloud.modelserver.glsp.model.EMSModelState;
import org.eclipse.glsp.server.model.GModelState;

public abstract class EMSNotationModelState extends EMSModelState {

   @Override
   public abstract EMSNotationModelIndex getIndex();

   public static EMSNotationModelState getModelState(final GModelState state) {
      if (!(state instanceof EMSNotationModelState)) {
         throw new IllegalArgumentException("Argument must be a EMSNotationModelState");
      }
      return ((EMSNotationModelState) state);
   }

}
