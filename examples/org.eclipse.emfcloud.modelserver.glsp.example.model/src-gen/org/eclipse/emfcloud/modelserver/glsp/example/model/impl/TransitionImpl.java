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
package org.eclipse.emfcloud.modelserver.glsp.example.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emfcloud.modelserver.glsp.example.model.ModelPackage;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Task;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Transition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.modelserver.glsp.example.model.impl.TransitionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.eclipse.emfcloud.modelserver.glsp.example.model.impl.TransitionImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TransitionImpl extends IdentifiableImpl implements Transition {
   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   protected TransitionImpl() {
      super();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   protected EClass eStaticClass() {
      return ModelPackage.Literals.TRANSITION;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Task getSource() {
      Task source = basicGetSource();
      return source != null && source.eIsProxy() ? (Task)eResolveProxy((InternalEObject)source) : source;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Task basicGetSource() {
      // TODO: implement this method to return the 'Source' reference
      // -> do not perform proxy resolution
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Task getTarget() {
      Task target = basicGetTarget();
      return target != null && target.eIsProxy() ? (Task)eResolveProxy((InternalEObject)target) : target;
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public Task basicGetTarget() {
      // TODO: implement this method to return the 'Target' reference
      // -> do not perform proxy resolution
      // Ensure that you remove @generated or mark it @generated NOT
      throw new UnsupportedOperationException();
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Object eGet(int featureID, boolean resolve, boolean coreType) {
      switch (featureID) {
         case ModelPackage.TRANSITION__SOURCE:
            if (resolve) return getSource();
            return basicGetSource();
         case ModelPackage.TRANSITION__TARGET:
            if (resolve) return getTarget();
            return basicGetTarget();
      }
      return super.eGet(featureID, resolve, coreType);
   }

   /**
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public boolean eIsSet(int featureID) {
      switch (featureID) {
         case ModelPackage.TRANSITION__SOURCE:
            return basicGetSource() != null;
         case ModelPackage.TRANSITION__TARGET:
            return basicGetTarget() != null;
      }
      return super.eIsSet(featureID);
   }

} //TransitionImpl
