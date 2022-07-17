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
package org.eclipse.emfcloud.modelserver.glsp.actions.handlers;

import org.eclipse.emfcloud.modelserver.glsp.EMSModelServerAccess;
import org.eclipse.emfcloud.modelserver.glsp.EMSModelState;
import org.eclipse.glsp.server.actions.AbstractActionHandler;
import org.eclipse.glsp.server.actions.Action;

import com.google.inject.Inject;

public abstract class AbstractEMSActionHandler<T extends Action>
   extends AbstractActionHandler<T> implements EMSActionHandler<T> {

   @Inject
   protected EMSModelState modelState;

   @Inject
   protected EMSModelServerAccess modelServerAccess;

}
