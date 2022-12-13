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
package org.eclipse.emfcloud.integration.example.modelserver;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emfcloud.modelserver.glsp.example.model.util.ModelResourceFactoryImpl;

public interface TaskListResource extends XMIResource {

   Resource.Factory FACTORY = new ModelResourceFactoryImpl();

   String FILE_EXTENSION = "tasklist";

}
