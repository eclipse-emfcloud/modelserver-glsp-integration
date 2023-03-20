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
import { GLSPDiagramLanguage } from '@eclipse-glsp/theia-integration/lib/common/glsp-diagram-language';

export const TaskListLanguage: GLSPDiagramLanguage = {
    contributionId: 'TaskList',
    label: 'TaskList Diagram',
    diagramType: 'tasklist-diagram',
    iconClass: 'codicon codicon-tasklist',
    fileExtensions: ['.tasklist']
};
