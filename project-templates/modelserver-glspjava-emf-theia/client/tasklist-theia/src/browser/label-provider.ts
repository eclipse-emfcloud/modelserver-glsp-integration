/********************************************************************************
 * Copyright (c) 2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
import { UriSelection } from '@theia/core';
import { LabelProviderContribution } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { injectable } from 'inversify';

import { TaskListLanguage } from '../common/tasklist-language';

@injectable()
export class TaskListTreeLabelProviderContribution implements LabelProviderContribution {
    canHandle(uri: object): number {
        let toCheck: any = uri;
        if (UriSelection.is(uri)) {
            toCheck = UriSelection.getUri(uri);
        }
        if (toCheck instanceof URI) {
            if (toCheck.path.ext === TaskListLanguage.fileExtensions[0]) {
                return 1000;
            }
        }
        return 0;
    }

    getIcon(): string {
        return `${TaskListLanguage.iconClass} default-file-icon`;
    }
}
