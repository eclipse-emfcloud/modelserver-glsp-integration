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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * Helper annotation that is used for injecting the semantic file extension string into classes of diagram session
 * (client session) specific injectors.
 *
 * Example usage:
 *
 * <pre>
 * &#64;Inject
 * &#64;SemanticFileExtension
 * protected String semanticFileExtension;
 * </pre>
 *
 */
@Target({ FIELD, PARAMETER, METHOD })
@Retention(RUNTIME)
@BindingAnnotation
public @interface SemanticFileExtension {}
