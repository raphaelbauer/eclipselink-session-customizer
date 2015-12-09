/**
 * Copyright (C) 2015 Zalando SE (http://tech.zalando.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zalando.jpa.eclipselink.customizer.databasemapping;

import org.eclipse.persistence.mappings.OneToManyMapping;
import org.eclipse.persistence.sessions.Session;

/**
 * @author  jbellmann
 */
public class OneToManyMappingColumnNameCustomizer extends AbstractColumnNameCustomizer<OneToManyMapping> {

    public OneToManyMappingColumnNameCustomizer() {
        super(OneToManyMapping.class);
    }

    @Override
    public void customizeColumnName(final String tableName, final OneToManyMapping databaseMapping,
            final Session session) {
        logFine(session, "Do nothing on 'customizeColumnName'-method for tableName {0}", tableName);
    }

}
