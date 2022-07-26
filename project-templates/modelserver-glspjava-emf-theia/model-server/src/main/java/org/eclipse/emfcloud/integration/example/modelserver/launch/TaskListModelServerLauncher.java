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
package org.eclipse.emfcloud.integration.example.modelserver.launch;

import java.io.IOException;

import org.eclipse.emfcloud.integration.example.modelserver.TaskListModelServerModule;
import org.eclipse.emfcloud.modelserver.emf.launch.CLIBasedModelServerLauncher;
import org.eclipse.emfcloud.modelserver.emf.launch.CLIParser;
import org.eclipse.emfcloud.modelserver.emf.launch.ModelServerLauncher;

public class TaskListModelServerLauncher {

   private static String EXECUTABLE_NAME = "org.eclipse.emfcloud.integration.example.modelserver-0.7.0-standalone.jar";

   public static void main(final String[] args) throws IOException {
      final ModelServerLauncher launcher = new CLIBasedModelServerLauncher(createCLIParser(args),
         new TaskListModelServerModule());
      launcher.run();
   }

   protected static CLIParser createCLIParser(final String[] args) {
      CLIParser parser = new CLIParser(args, CLIParser.getDefaultCLIOptions(), EXECUTABLE_NAME, 8081);
      return parser;
   }

}
