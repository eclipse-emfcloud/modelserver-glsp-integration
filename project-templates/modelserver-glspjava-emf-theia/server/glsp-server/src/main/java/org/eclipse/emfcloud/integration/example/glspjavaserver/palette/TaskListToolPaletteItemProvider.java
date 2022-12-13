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
package org.eclipse.emfcloud.integration.example.glspjavaserver.palette;

import java.util.List;
import java.util.Map;

import org.eclipse.emfcloud.integration.example.glspjavaserver.TaskListModelTypes;
import org.eclipse.glsp.server.actions.TriggerEdgeCreationAction;
import org.eclipse.glsp.server.actions.TriggerNodeCreationAction;
import org.eclipse.glsp.server.features.toolpalette.PaletteItem;
import org.eclipse.glsp.server.features.toolpalette.ToolPaletteItemProvider;

import com.google.common.collect.Lists;

public class TaskListToolPaletteItemProvider implements ToolPaletteItemProvider {

   @Override
   public List<PaletteItem> getItems(final Map<String, String> args) {
      return Lists.newArrayList(nodes(), edges());
   }

   private PaletteItem nodes() {
      PaletteItem createTask = node(TaskListModelTypes.TASK, "Task");
      List<PaletteItem> nodes = Lists.newArrayList(createTask);
      return PaletteItem.createPaletteGroup("nodes", "Nodes", nodes, "symbol-property");
   }

   private PaletteItem node(final String elementTypeId, final String label) {
      return new PaletteItem(elementTypeId, label, new TriggerNodeCreationAction(elementTypeId));
   }

   private PaletteItem edges() {
      PaletteItem createTransition = edge(TaskListModelTypes.TRANSITION, "Transition");
      List<PaletteItem> edges = Lists.newArrayList(createTransition);
      return PaletteItem.createPaletteGroup("edges", "Edges", edges, "symbol-property");
   }

   private PaletteItem edge(final String elementTypeId, final String label) {
      return new PaletteItem(elementTypeId, label, new TriggerEdgeCreationAction(elementTypeId));
   }

}
