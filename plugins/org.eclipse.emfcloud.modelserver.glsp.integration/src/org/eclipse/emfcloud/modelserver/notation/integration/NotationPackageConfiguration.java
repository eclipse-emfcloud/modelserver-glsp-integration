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
package org.eclipse.emfcloud.modelserver.notation.integration;

import java.util.Collection;

import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.glsp.server.emf.model.notation.NotationPackage;

import com.google.common.collect.Lists;

public class NotationPackageConfiguration implements EPackageConfiguration {

   @Override
   public String getId() { return NotationPackage.eINSTANCE.getNsURI(); }

   @Override
   public Collection<String> getFileExtensions() { return Lists.newArrayList(NotationResource.FILE_EXTENSION); }

   @Override
   public void registerEPackage() {
      NotationPackage.eINSTANCE.eClass();
   }

}
