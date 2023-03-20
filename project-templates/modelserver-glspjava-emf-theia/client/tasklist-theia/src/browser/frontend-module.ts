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
import {
    ContainerContext,
    DiagramConfiguration,
    GLSPClientContribution,
    GLSPTheiaFrontendModule,
    TheiaGLSPConnector
} from '@eclipse-glsp/theia-integration';
import { LabelProviderContribution } from '@theia/core/lib/browser';

import { TaskListLanguage } from '../common/tasklist-language';
import { TaskListDiagramConfiguration } from './diagram/diagram-configuration';
import { TaskListTheiaGLSPConnector } from './diagram/theia-glsp-connector';
import { TaskListGLSPClientContribution } from './glsp-client-contribution';
import { TaskListTreeLabelProviderContribution } from './label-provider';

export class TaskListTheiaFrontendModule extends GLSPTheiaFrontendModule {
    readonly diagramLanguage = TaskListLanguage;

    override bindTheiaGLSPConnector(context: ContainerContext): void {
        context.bind(TheiaGLSPConnector).toDynamicValue(dynamicContext => {
            const connector = dynamicContext.container.resolve(TaskListTheiaGLSPConnector);
            connector.doConfigure(this.diagramLanguage);
            return connector;
        });
    }

    bindDiagramConfiguration(context: ContainerContext): void {
        context.bind(DiagramConfiguration).to(TaskListDiagramConfiguration);
    }

    override bindGLSPClientContribution(context: ContainerContext): void {
        context.bind(GLSPClientContribution).to(TaskListGLSPClientContribution);
    }

    override configure(context: ContainerContext): void {
        super.configure(context);
        context.bind(LabelProviderContribution).to(TaskListTreeLabelProviderContribution).inSingletonScope();
    }
}

export default new TaskListTheiaFrontendModule();
