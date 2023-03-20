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
    configureDefaultModelElements,
    ConsoleLogger,
    ContainerConfiguration,
    initializeDiagramContainer,
    LogLevel,
    overrideViewerOptions,
    TYPES
} from '@eclipse-glsp/client';
import { Container, ContainerModule } from '@theia/core/shared/inversify';
import '../css/diagram.css';

const tasklistDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope();
    rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn);
    const context = { bind, unbind, isBound, rebind };
    configureDefaultModelElements(context);
});

export default function initializeTaskListDiagramContainer(
    container: Container,
    widgetId: string,
    ...containerConfiguration: ContainerConfiguration
): Container {
    initializeDiagramContainer(container, tasklistDiagramModule, ...containerConfiguration);

    overrideViewerOptions(container, {
        baseDiv: widgetId,
        hiddenDiv: widgetId + '_hidden',
        needsClientLayout: true
    });

    return container;
}
