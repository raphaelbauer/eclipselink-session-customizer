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

import java.util.Vector;

import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.Association;
import org.eclipse.persistence.mappings.OneToOneMapping;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.customizer.NameUtils;
import org.zalando.jpa.eclipselink.customizer.databasemapping.support.EntityFieldInspector;
import org.zalando.jpa.eclipselink.customizer.databasemapping.support.JoinColumnFieldInspector;

/**
 * TODO check duplicate code with {@link OneToManyMappingColumnNameCustomizer}.
 *
 * @author  jbellmann
 */
public class OneToOneMappingColumnNameCustomizer extends AbstractColumnNameCustomizer<OneToOneMapping> {

    public OneToOneMappingColumnNameCustomizer() {
        super(OneToOneMapping.class);
    }

    @Override
    public void customizeColumnName(final String tableName, final OneToOneMapping databaseMapping,
            final Session session) {
        logDatabaseMapping(databaseMapping, session);

        EntityFieldInspector<?> entityFieldInspector = new JoinColumnFieldInspector(FieldInspector.getFieldInspector(
                    databaseMapping).getField());
        for (final DatabaseField foreignKeyField : databaseMapping.getForeignKeyFields()) {
            String prefix = NameUtils.iconizeTableName(tableName) + "_";
            if (!foreignKeyField.getName().startsWith(prefix) && !(entityFieldInspector.isNameValueSet())) {

                String newFieldName = NameUtils.buildFieldName(tableName, databaseMapping.getAttributeName()) + "_id";

                foreignKeyField.setName(newFieldName);
                logFine(session, "ForeignKeyField-Name was set to {0}", foreignKeyField.getName());

            } else if (!foreignKeyField.getName().startsWith(prefix) && entityFieldInspector.isNameValueSet()) {

                String newFieldName = NameUtils.buildFieldName(tableName, foreignKeyField.getName());
                foreignKeyField.setName(newFieldName);
                logFine(session, "ForeignKeyField-Name was set to {0}", foreignKeyField.getName());
            }
        }

        Vector associations = databaseMapping.getSourceToTargetKeyFieldAssociations();
        for (final Object ass : associations) {
            logFine(session, "--" + tableName + "  --  " + ((Association) ass).getKey().toString() + "----");
        }

    }

}
