/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.modelserver.glsp;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emfcloud.modelserver.client.JsonToStringSubscriptionListener;
import org.eclipse.emfcloud.modelserver.glsp.actions.EMSRefreshModelAction;
import org.eclipse.glsp.server.actions.ActionDispatcher;
import org.eclipse.glsp.server.actions.SetDirtyStateAction;

public class EMSSubscriptionListener extends JsonToStringSubscriptionListener {

   private static final Logger LOGGER = LogManager.getLogger(EMSSubscriptionListener.class);

   protected final String modelUri;
   protected final ActionDispatcher actionDispatcher;

   public EMSSubscriptionListener(final String modelUri, final ActionDispatcher actionDispatcher) {
      this.modelUri = modelUri;
      this.actionDispatcher = actionDispatcher;
   }

   protected void logResponse(final String message) {
      LOGGER.debug("[" + URI.createURI(modelUri).lastSegment() + "]: " + message);
   }

   @Override
   public void onIncrementalUpdate(final String incrementalUpdate) {
      logResponse("Incremental <JsonPatch> update from model server received: " + incrementalUpdate);
      this.refresh();
   }

   @Override
   public void onFullUpdate(final String fullUpdate) {
      logResponse("Full <JsonString> update from model server received: " + fullUpdate);
      this.refresh();
   }

   protected void refresh() {
      actionDispatcher.dispatch(new EMSRefreshModelAction());
   }

   @Override
   public void onDirtyChange(final boolean isDirty) {
      logResponse("Dirty State Changed: " + isDirty);
      actionDispatcher.dispatch(new SetDirtyStateAction(isDirty));
   }

   @Override
   public void onError(final Optional<String> message) {
      logResponse("Error from model server received: " + message.get());
   }

   @Override
   public void onClosing(final int code, final String reason) {
      logResponse("Closing connection to model server, reason: " + reason);
   }

   @Override
   public void onClosed(final int code, final String reason) {
      logResponse("Closed connection to model server, reason: " + reason);
   }

}
