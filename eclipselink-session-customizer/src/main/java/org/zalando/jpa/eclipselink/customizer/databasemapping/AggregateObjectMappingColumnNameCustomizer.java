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

import java.lang.reflect.Field;
import java.util.Map;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.AggregateObjectMapping;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.customizer.NameUtils;

/**
 * This ColumnNameCustomizer is for {@link Embeddable} types in {@link Entity}.
 *
 * @author  jbellmann
 */
public class AggregateObjectMappingColumnNameCustomizer extends AbstractColumnNameCustomizer<AggregateObjectMapping> {

    public AggregateObjectMappingColumnNameCustomizer() {
        super(AggregateObjectMapping.class);
    }

    @Override
    public void customizeColumnName(final String tableName, final AggregateObjectMapping databaseMapping,
            final Session session) {

        Map<String, DatabaseField> map = databaseMapping.getAggregateToSourceFields();

        if (!map.isEmpty()) {

            logFine(session, "Seems there where annotations for AttributeOverride");
            return;

        } else {

            Class<?> referenceClass = databaseMapping.getReferenceClass();
            Field[] fields = referenceClass.getDeclaredFields();
            if (fields.length > 0) {
                for (Field f : fields) {

                    String fieldName = f.getName();
                    DatabaseField databaseField = new DatabaseField();
                    String databaseFieldName = NameUtils.buildFieldName(tableName, fieldName);

                    databaseField.setName(databaseFieldName);
                    map.put(fieldName.toUpperCase(), databaseField);

                    logFine(session, "\tAggregate-Field " + fieldName + " --> " + databaseFieldName);
                }
            }
        }
    }

}
