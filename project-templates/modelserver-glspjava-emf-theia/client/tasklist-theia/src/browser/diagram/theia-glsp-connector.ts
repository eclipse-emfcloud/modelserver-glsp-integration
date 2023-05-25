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
import { Args } from '@eclipse-glsp/protocol';
import { BaseTheiaGLSPConnector } from '@eclipse-glsp/theia-integration/lib/browser/diagram/base-theia-glsp-connector';
import { GLSPTheiaDiagramServer } from '@eclipse-glsp/theia-integration/lib/browser/diagram/glsp-theia-diagram-server';
import { GLSPDiagramLanguage } from '@eclipse-glsp/theia-integration/lib/common/glsp-diagram-language';
import { injectable } from 'inversify';
import { TaskListLanguage } from '../../common/tasklist-language';

@injectable()
export class TaskListTheiaGLSPConnector extends BaseTheiaGLSPConnector {
    private _diagramType: string = TaskListLanguage.diagramType;
    private _contributionId: string = TaskListLanguage.contributionId;

    public doConfigure(diagramLanguage: GLSPDiagramLanguage): void {
        this._contributionId = diagramLanguage.contributionId;
        this._diagramType = diagramLanguage.diagramType;
        this.initialize();
    }

    get diagramType(): string {
        if (!this._diagramType) {
            throw new Error('No diagramType has been set for this ConfigurableTheiaGLSPConnector');
        }
        return this._diagramType;
    }

    get contributionId(): string {
        if (!this._contributionId) {
            throw new Error('No contributionId has been set for this ConfigurableTheiaGLSPConnector');
        }
        return this._contributionId;
    }

    protected override initialize(): void {
        if (this._diagramType && this._contributionId) {
            super.initialize();
        }
    }

    override disposeClientSessionArgs(diagramServer: GLSPTheiaDiagramServer): Args | undefined {
        return {
            ['sourceUri']: diagramServer.sourceURI
        };
    }
}
