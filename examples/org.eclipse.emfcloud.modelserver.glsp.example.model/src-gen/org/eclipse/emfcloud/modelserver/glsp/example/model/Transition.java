/**
 * Copyright (c) 2022 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
package org.eclipse.emfcloud.modelserver.glsp.example.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.modelserver.glsp.example.model.Transition#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.modelserver.glsp.example.model.Transition#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.modelserver.glsp.example.model.ModelPackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends Identifiable {
   /**
    * Returns the value of the '<em><b>Source</b></em>' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Source</em>' reference.
    * @see org.eclipse.emfcloud.modelserver.glsp.example.model.ModelPackage#getTransition_Source()
    * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
    * @generated
    */
   Task getSource();

   /**
    * Returns the value of the '<em><b>Target</b></em>' reference.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @return the value of the '<em>Target</em>' reference.
    * @see org.eclipse.emfcloud.modelserver.glsp.example.model.ModelPackage#getTransition_Target()
    * @model required="true" transient="true" changeable="false" volatile="true" derived="true"
    * @generated
    */
   Task getTarget();

} // Transition
