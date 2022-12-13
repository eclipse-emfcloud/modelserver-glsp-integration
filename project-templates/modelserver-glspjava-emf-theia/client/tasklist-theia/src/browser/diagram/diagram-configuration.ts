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
import { createTaskListDiagramContainer } from '@eclipse-emfcloud/tasklist-glsp';
import { configureDiagramServer, GLSPDiagramConfiguration, GLSPTheiaDiagramServer } from '@eclipse-glsp/theia-integration/lib/browser';
import { Container, injectable } from '@theia/core/shared/inversify';
import 'sprotty-theia/css/theia-sprotty.css';
import { TaskListLanguage } from '../../common/tasklist-language';

@injectable()
export class TaskListDiagramConfiguration extends GLSPDiagramConfiguration {
    diagramType: string = TaskListLanguage.diagramType;

    doCreateContainer(widgetId: string): Container {
        const container = createTaskListDiagramContainer(widgetId);
        configureDiagramServer(container, GLSPTheiaDiagramServer);
        return container;
    }
}
