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
package org.eclipse.emfcloud.modelserver.integration;

import static org.eclipse.emfcloud.modelserver.emf.common.DefaultModelURIConverter.MODEL_URI_VALIDATOR;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emfcloud.modelserver.common.APIVersionRange;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV2;
import org.eclipse.emfcloud.modelserver.common.utils.MapBinding;
import org.eclipse.emfcloud.modelserver.emf.di.DefaultModelServerModule;

import com.google.common.base.Predicates;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

public abstract class EMSModelServerModule extends DefaultModelServerModule {

   @Override
   protected void configure() {
      super.configure();
      bind(String.class).annotatedWith(SemanticFileExtension.class).toInstance(getSemanticFileExtension());
      bind(String.class).annotatedWith(SemanticModelFormat.class).toInstance(getSemanticModelFormat());
   }

   protected abstract String getSemanticFileExtension();

   protected String getSemanticModelFormat() { return ModelServerPathParametersV2.FORMAT_JSON_V2; }

   @Override
   protected void configureModelURIResolvers(
      final MapBinding<APIVersionRange, Function<? super URI, Optional<URI>>> binding) {

      super.configureModelURIResolvers(binding);
      // Accept the absolute file: URIs sent in by the theia client
      bind(new TypeLiteral<Predicate<? super URI>>() {}).annotatedWith(Names.named(MODEL_URI_VALIDATOR))
         .toInstance(Predicates.alwaysTrue());
   }

   @Override
   protected AdapterFactory provideAdapterFactory() {
      AdapterFactory adapterFactory = super.provideAdapterFactory();
      AdapterFactory reflectiveFactory = new ReflectiveItemProviderAdapterFactory();

      if (adapterFactory instanceof ComposedAdapterFactory) {
         ((ComposedAdapterFactory) adapterFactory).addAdapterFactory(reflectiveFactory);
         return adapterFactory;
      }
      return reflectiveFactory;
   }

}
