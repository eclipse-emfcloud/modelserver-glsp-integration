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
package org.eclipse.emfcloud.integration.example.glspjavaserver;

import java.util.List;

import org.eclipse.glsp.server.diagram.BaseDiagramConfiguration;
import org.eclipse.glsp.server.layout.ServerLayoutKind;
import org.eclipse.glsp.server.types.EdgeTypeHint;
import org.eclipse.glsp.server.types.ShapeTypeHint;

public class TaskListDiagramConfiguration extends BaseDiagramConfiguration {

   @Override
   public List<ShapeTypeHint> getShapeTypeHints() {
      // tasks can be moved, deleted and resized, but not re-parented
      return List.of(new ShapeTypeHint(TaskListModelTypes.TASK, true, true, true, false));
   }

   @Override
   public List<EdgeTypeHint> getEdgeTypeHints() {
      // transitions can be deleted and routed, but not repositioned
      return List.of(new EdgeTypeHint(TaskListModelTypes.TRANSITION, false, true, true,
         List.of(TaskListModelTypes.TASK), List.of(TaskListModelTypes.TASK)));
   }

   @Override
   public ServerLayoutKind getLayoutKind() { return ServerLayoutKind.MANUAL; }

}
