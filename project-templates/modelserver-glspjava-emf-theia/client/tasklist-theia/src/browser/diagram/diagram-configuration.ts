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
import { initializeTaskListDiagramContainer } from '@eclipse-emfcloud/tasklist-glsp';
import { ContainerConfiguration } from '@eclipse-glsp/client';
import '@eclipse-glsp/theia-integration/css/sprotty-theia.css';
import { configureDiagramServer, GLSPDiagramConfiguration, GLSPTheiaDiagramServer } from '@eclipse-glsp/theia-integration/lib/browser';
import { Container, injectable } from '@theia/core/shared/inversify';
import { TaskListLanguage } from '../../common/tasklist-language';

@injectable()
export class TaskListDiagramConfiguration extends GLSPDiagramConfiguration {
    diagramType: string = TaskListLanguage.diagramType;

    configureContainer(container: Container, widgetId: string, ...containerConfiguration: ContainerConfiguration): Container {
        initializeTaskListDiagramContainer(container, widgetId, ...containerConfiguration);
        configureDiagramServer(container, GLSPTheiaDiagramServer);
        return container;
    }
}
