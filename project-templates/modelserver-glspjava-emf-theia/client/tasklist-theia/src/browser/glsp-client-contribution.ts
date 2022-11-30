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
import { Args, MaybePromise } from '@eclipse-glsp/client';
import { BaseGLSPClientContribution } from '@eclipse-glsp/theia-integration/lib/browser';
import { injectable } from 'inversify';

import { TaskListLanguage } from '../common/tasklist-language';

export interface TaskListInitializeOptions {
    timestamp: Date;
    modelServerURL: string;
}

@injectable()
export class TaskListGLSPClientContribution extends BaseGLSPClientContribution {
    readonly id = TaskListLanguage.contributionId;
    readonly fileExtensions = TaskListLanguage.fileExtensions;

    protected override createInitializeOptions(): MaybePromise<Args | undefined> {
        return {
            ['timestamp']: new Date().toString(),
            ['modelServerURL']: 'http://localhost:8081/api/v2/'
        };
    }
}
