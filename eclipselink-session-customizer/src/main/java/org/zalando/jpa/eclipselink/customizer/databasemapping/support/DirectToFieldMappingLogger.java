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
package org.zalando.jpa.eclipselink.customizer.databasemapping.support;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.LogSupport;

/**
 * Logs informations about the {@link DatabaseMapping}.
 *
 * @author  jbellmann
 */
public class DirectToFieldMappingLogger extends LogSupport implements DatabaseMappingLogger<DirectToFieldMapping> {

    @Override
    public void logDatabaseMapping(final DirectToFieldMapping databaseMapping, final Session session) {

        logFine(session, "\tmapping.attributeName : {0}", databaseMapping.getAttributeName());
        logFine(session, "\tmapping.attributeClassification: {0}", databaseMapping.getAttributeClassification());
        logFine(session, "\tmapping.field.name : {0}", databaseMapping.getField().getName());
        logFine(session, "\tmapping.field.sqlType : {0}", databaseMapping.getField().getSqlType());
        logFine(session, "\tmapping.field.typeName: {0}", databaseMapping.getField().getTypeName());
        logFine(session, "\tmapping.field.columnDefinition : {0}", databaseMapping.getField().getColumnDefinition());
        logFine(session, "\tmapping.fieldClassfication : {0}", databaseMapping.getFieldClassification());
    }

    @Override
    public Class<DirectToFieldMapping> getMappingType() {
        return DirectToFieldMapping.class;
    }
}
