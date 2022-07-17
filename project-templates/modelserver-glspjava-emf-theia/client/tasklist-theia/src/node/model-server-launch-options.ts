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
import { LaunchOptions } from '@eclipse-emfcloud/modelserver-theia/lib/node';
import { injectable } from '@theia/core/shared/inversify';
import { join, resolve } from 'path';

const JAR_FILE_PATH = resolve(
    join(
        __dirname,
        '..',
        '..',
        '..',
        '..',
        'server',
        'model-server',
        'target',
        'org.eclipse.emfcloud.integration.example.modelserver-0.7.0-SNAPSHOT-standalone.jar'
    )
);

const LOG_FILE_PATH = resolve(join(__dirname, '..', '..', '..', '..', 'server', 'model-server', 'log4j2-embedded.xml'));

const WORKSPACE_FILE_PATH = resolve(join(__dirname, '..', '..', '..', 'workspace'));

/** Options for the `ModelServerLauncher` to use to start the Model Server */
@injectable()
export class TaskListModelServerLaunchOptions implements LaunchOptions {
    baseURL = 'api/v2/';
    serverPort = 8081;
    hostname = 'localhost';
    jarPath = JAR_FILE_PATH;
    additionalArgs = [`-r=${WORKSPACE_FILE_PATH}`, `-l=${LOG_FILE_PATH}`];
}
