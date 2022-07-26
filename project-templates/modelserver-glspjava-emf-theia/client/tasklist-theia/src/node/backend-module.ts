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
import { GLSPServerContribution } from '@eclipse-glsp/theia-integration/lib/node';
import { ContainerModule } from '@theia/core/shared/inversify';
import { TaskListGLSPServerContribution } from './glsp-server-contribution';
import { TaskListModelServerLaunchOptions } from './model-server-launch-options';

export default new ContainerModule((bind, _unbind, isBound, rebind) => {
    if (isBound(LaunchOptions)) {
        rebind(LaunchOptions).to(TaskListModelServerLaunchOptions).inSingletonScope();
    } else {
        bind(LaunchOptions).to(TaskListModelServerLaunchOptions).inSingletonScope();
    }

    bind(TaskListGLSPServerContribution).toSelf().inSingletonScope();
    bind(GLSPServerContribution).toService(TaskListGLSPServerContribution);
});
