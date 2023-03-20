/********************************************************************************
 * Copyright (c) 2022-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.integration.example.glspjavaserver.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.integration.example.glspjavaserver.TaskListModelTypes;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Task;
import org.eclipse.emfcloud.modelserver.glsp.example.model.TaskList;
import org.eclipse.emfcloud.modelserver.glsp.example.model.Transition;
import org.eclipse.emfcloud.modelserver.glsp.notation.integration.EMSNotationGModelFactory;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.builder.impl.GEdgeBuilder;
import org.eclipse.glsp.graph.builder.impl.GLabelBuilder;
import org.eclipse.glsp.graph.builder.impl.GLayoutOptions;
import org.eclipse.glsp.graph.builder.impl.GNodeBuilder;
import org.eclipse.glsp.graph.util.GConstants;
import org.eclipse.glsp.server.emf.model.notation.Diagram;

public class TaskListGModelFactory extends EMSNotationGModelFactory {

   @Override
   protected void fillRootElement(final EObject semanticModel, final Diagram notationModel, final GModelRoot newRoot) {
      TaskList taskList = TaskList.class.cast(semanticModel);
      GGraph graph = GGraph.class.cast(newRoot);
      if (notationModel.getSemanticElement() != null
         && notationModel.getSemanticElement().getResolvedSemanticElement() != null) {

         // create task nodes
         taskList.getTasks().stream()
            .map(this::createTaskNode)
            .forEachOrdered(graph.getChildren()::add);

         // create transition edges
         taskList.getTransitions().stream()
            .map(transition -> this.createTransitionEdge(graph, transition))
            .forEachOrdered(graph.getChildren()::add);
      }
   }

   protected GNode createTaskNode(final Task task) {
      GNodeBuilder taskNodeBuilder = new GNodeBuilder(TaskListModelTypes.TASK)
         .id(idGenerator.getOrCreateId(task))
         .addCssClass("tasklist-node")
         .layout(GConstants.Layout.HBOX)
         .layoutOptions(new GLayoutOptions().vAlign(GConstants.VAlign.TOP))
         .add(new GLabelBuilder(DefaultTypes.LABEL).text(task.getName()).build());

      applyShapeData(task, taskNodeBuilder);
      return taskNodeBuilder.build();
   }

   protected GEdge createTransitionEdge(final GGraph graph, final Transition transition) {
      String sourceId = transition.getSource().getId();
      String targetId = transition.getTarget().getId();

      GModelElement sourceNode = findGNodeById(graph.getChildren(), sourceId);
      GModelElement targetNode = findGNodeById(graph.getChildren(), targetId);

      GEdgeBuilder builder = new GEdgeBuilder(TaskListModelTypes.TRANSITION)
         .source(sourceNode)
         .target(targetNode)
         .id(idGenerator.getOrCreateId(transition));

      applyEdgeData(transition, builder);
      return builder.build();
   }

   protected GModelElement findGNodeById(final EList<GModelElement> eList, final String elementId) {
      return eList.stream().filter(node -> elementId.equals(node.getId())).findFirst().orElse(null);
   }

}
