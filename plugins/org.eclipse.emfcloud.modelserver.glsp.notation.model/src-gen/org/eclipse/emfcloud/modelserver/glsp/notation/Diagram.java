/**
 * Copyright (c) 2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
package org.eclipse.emfcloud.modelserver.glsp.notation;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Diagram</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.modelserver.glsp.notation.Diagram#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.modelserver.glsp.notation.NotationPackage#getDiagram()
 * @model
 * @generated
 */
public interface Diagram extends NotationElement {
   /**
    * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
    * The list contents are of type {@link org.eclipse.emfcloud.modelserver.glsp.notation.NotationElement}.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Elements</em>' containment reference list.
    * @see org.eclipse.emfcloud.modelserver.glsp.notation.NotationPackage#getDiagram_Elements()
    * @model containment="true"
    * @generated
    */
   EList<NotationElement> getElements();

} // Diagram
