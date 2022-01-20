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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.modelserver.glsp.model.RootSemanticToGModelTransformer;
import org.eclipse.emfcloud.modelserver.glsp.notation.Diagram;
import org.eclipse.emfcloud.modelserver.glsp.notation.Edge;
import org.eclipse.emfcloud.modelserver.glsp.notation.NotationElement;
import org.eclipse.emfcloud.modelserver.glsp.notation.NotationFactory;
import org.eclipse.emfcloud.modelserver.glsp.notation.SemanticProxy;
import org.eclipse.emfcloud.modelserver.glsp.notation.Shape;
import org.eclipse.glsp.graph.GEdge;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelRoot;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.GShapeElement;
import org.eclipse.glsp.graph.util.GraphUtil;
import org.eclipse.glsp.server.features.core.model.GModelFactory;
import org.eclipse.glsp.server.model.GModelState;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

public class DefaultModelFactory implements GModelFactory {

   @Inject
   GModelState gModelState;

   @SuppressWarnings("rawtypes")
   @Inject
   RootSemanticToGModelTransformer gModelTransformder;

   @Override
   public void createGModel() {
      DefaultModelState modelState = DefaultModelState.getModelState(gModelState);

      GModelRoot gmodelRoot = gModelTransformder.createRoot();

      DefaultModelIndex modelIndex = modelState.getIndex();
      modelIndex.clear();

      EObject semanticModel = modelState.getSemanticModel();
      EcoreUtil.resolveAll(semanticModel);
      Diagram diagram = modelState.getNotationModel();
      getOrCreateDiagram(diagram, semanticModel, modelIndex);

      gmodelRoot = gModelTransformder.create();
      initialize(gmodelRoot, modelIndex, semanticModel, diagram);

      modelState.setRoot(gmodelRoot);
      System.out.println(modelState.isDirty());
      modelState.getModelServerAccess().save().thenApply(res -> {
         System.out.println(res.body());
         System.out.println(modelState.isDirty());
         return null;
      });

   }

   protected Diagram getOrCreateDiagram(final Diagram diagram, final EObject model,
      final DefaultModelIndex modelIndex) {
      if (diagram == null) {
         createDiagram(model);
      }
      findUnresolvedElements(diagram, model)
         .forEach(e -> e.setSemanticElement(resolved(e.getSemanticElement(), model)));
      modelIndex.indexNotation(diagram);
      return diagram;
   }

   protected Diagram initialize(final GModelRoot gRoot, final DefaultModelIndex modelIndex, final EObject model,
      final Diagram diagram) {
      Preconditions.checkArgument(diagram.getSemanticElement().getResolvedElement() == model);
      gRoot.getChildren().forEach(child -> {
         modelIndex.getNotation(child).ifPresentOrElse(n -> updateNotationElement(n, child),
            () -> initializeNotationElement(child, modelIndex).ifPresent(diagram.getElements()::add));
      });
      return diagram;
   }

   protected Optional<? extends NotationElement> initializeNotationElement(final GModelElement gModelElement,
      final DefaultModelIndex modelIndex) {
      Optional<? extends NotationElement> result = Optional.empty();
      if (gModelElement instanceof GNode) {
         result = initializeShape((GNode) gModelElement, modelIndex);
      } else if (gModelElement instanceof GEdge) {
         result = initializeEdge((GEdge) gModelElement, modelIndex);
      }
      return result;
   }

   protected List<NotationElement> findUnresolvedElements(final Diagram diagram, final EObject model) {
      List<NotationElement> unresolved = new ArrayList<>();

      if (diagram.getSemanticElement() == null
         || resolved(diagram.getSemanticElement(), model).getResolvedElement() == null) {
         unresolved.add(diagram);
      }

      unresolved.addAll(diagram.getElements().stream()
         .filter(element -> element.getSemanticElement() == null ? false
            : resolved(element.getSemanticElement(), model).getResolvedElement() == null)
         .collect(Collectors.toList()));

      return unresolved;
   }

   protected Diagram createDiagram(final EObject model) {
      Diagram diagram = NotationFactory.eINSTANCE.createDiagram();
      diagram.setSemanticElement(createProxy(model));
      return diagram;
   }

   protected Optional<Shape> initializeShape(final GShapeElement shapeElement, final DefaultModelIndex modelIndex) {
      return modelIndex.getSemantic(shapeElement)
         .map(semanticElement -> initializeShape(semanticElement, shapeElement, modelIndex));
   }

   protected Shape initializeShape(final EObject semanticElement, final GShapeElement shapeElement,
      final DefaultModelIndex modelIndex) {
      Shape shape = NotationFactory.eINSTANCE.createShape();
      shape.setSemanticElement(createProxy(semanticElement));
      if (shapeElement != null) {
         updateShape(shape, shapeElement);
      }
      modelIndex.indexNotation(shape);
      return shape;
   }

   protected Optional<Edge> initializeEdge(final GEdge gEdge, final DefaultModelIndex modelIndex) {
      return modelIndex.getSemantic(gEdge).map(semanticElement -> initializeEdge(semanticElement, gEdge, modelIndex));
   }

   protected Edge initializeEdge(final EObject semanticElement, final GEdge gEdge, final DefaultModelIndex modelIndex) {
      Edge edge = NotationFactory.eINSTANCE.createEdge();
      edge.setSemanticElement(createProxy(semanticElement));
      if (gEdge != null) {
         updateEdge(edge, gEdge);
      }
      modelIndex.indexNotation(edge);
      return edge;
   }

   protected SemanticProxy createProxy(final EObject eObject) {
      SemanticProxy proxy = NotationFactory.eINSTANCE.createSemanticProxy();
      proxy.setResolvedElement(eObject);
      proxy.setUri(EcoreUtil.getURI(eObject).fragment().toString());
      return proxy;
   }

   protected SemanticProxy resolved(final SemanticProxy proxy, final EObject model) {
      if (proxy.getResolvedElement() != null) {
         return proxy;
      }
      return reResolved(proxy, model);
   }

   protected SemanticProxy reResolved(final SemanticProxy proxy, final EObject model) {
      // The xmi:id is used as URI to identify elements, we use the underlying resource to fetch elements by id
      Resource res = model.eResource();
      proxy.setResolvedElement(res.getEObject(proxy.getUri()));
      return proxy;
   }

   protected void updateNotationElement(final NotationElement notation, final GModelElement modelElement) {
      if (notation instanceof Shape && modelElement instanceof GShapeElement) {
         updateShape((Shape) notation, (GShapeElement) modelElement);
      } else if (notation instanceof Edge && modelElement instanceof GEdge) {
         updateEdge((Edge) notation, (GEdge) modelElement);
      }
   }

   protected void updateShape(final Shape shape, final GShapeElement shapeElement) {
      if (shapeElement.getSize() != null) {
         shape.setSize(GraphUtil.copy(shapeElement.getSize()));
      }
      if (shapeElement.getPosition() != null) {
         shape.setPosition(GraphUtil.copy(shapeElement.getPosition()));
      } else if (shape.getPosition() != null) {
         shapeElement.setPosition(GraphUtil.copy(shape.getPosition()));
      }
   }

   protected void updateEdge(final Edge edge, final GEdge gEdge) {
      edge.getBendPoints().clear();
      if (gEdge.getRoutingPoints() != null) {
         ArrayList<GPoint> gPoints = new ArrayList<>();
         gEdge.getRoutingPoints().forEach(p -> gPoints.add(GraphUtil.copy(p)));
         edge.getBendPoints().addAll(gPoints);
      }
   }

}
