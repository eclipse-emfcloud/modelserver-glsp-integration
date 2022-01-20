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
package org.eclipse.emfcloud.modelserver.glsp.ids;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.modelserver.client.ModelServerClient;
import org.eclipse.emfcloud.modelserver.common.ModelServerPathParametersV1;
import org.eclipse.emfcloud.modelserver.common.codecs.DecodingException;
import org.eclipse.emfcloud.modelserver.common.codecs.DefaultJsonCodec;
import org.eclipse.emfcloud.modelserver.common.codecs.EncodingException;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;

public class DefaultModelServerClient extends ModelServerClient {

   public DefaultModelServerClient(final String baseUrl, final EPackageConfiguration... configurations)
      throws MalformedURLException {
      super(baseUrl, configurations);
   }

   @Override
   protected boolean isSupportedFormat(final String format) {
      return DEFAULT_SUPPORTED_FORMATS.contains(format)
         || Arrays.asList(this.configurations).stream().anyMatch(e -> e.getFileExtensions().contains(format));
   }

   @Override
   public String encode(final EObject eObject, final String format) {
      try {
         if (format.equals(ModelServerPathParametersV1.FORMAT_JSON)) {
            return new DefaultJsonCodec().encode(eObject).toString();
         }
         return new DefaultXmiCodec().encode(eObject, format).asText();
      } catch (EncodingException e) {
         LOG.error("Encoding of " + eObject + " with " + format + " format failed");
         throw new RuntimeException(e);
      }
   }

   @Override
   public Optional<EObject> decode(final String payload, final String format) {
      try {
         if (format.equals(ModelServerPathParametersV1.FORMAT_JSON)) {
            return new DefaultJsonCodec().decode(payload);
         }
         return new DefaultXmiCodec().decode(payload, format);

      } catch (DecodingException e) {
         LOG.error("Decoding of " + payload + " with " + format + " format failed");
      }
      return Optional.empty();
   }
}
