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
import java.util.Vector;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.eclipse.persistence.descriptors.RelationalDescriptor;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.internal.helper.DatabaseTable;
import org.eclipse.persistence.mappings.ManyToManyMapping;
import org.eclipse.persistence.mappings.RelationTableMechanism;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.customizer.NameUtils;

/**
 *
 * @author  jbellmann
 */
public class ManyToManyMappingCustomizer extends AbstractColumnNameCustomizer<ManyToManyMapping> {

    public ManyToManyMappingCustomizer() {
        super(ManyToManyMapping.class);
    }

    @Override
    public void customizeColumnName(final String tableName, final ManyToManyMapping databaseMapping,
            final Session session) {

        // wie heist dass attribute in der beinhaltenden Klasse
        final String attributeName = databaseMapping.getAttributeName(); // externalSystems

        // Typ der Collection zum Attribute
        Class<?> referenceClazz = databaseMapping.getReferenceClass();

        final RelationalDescriptor descriptor = (RelationalDescriptor) databaseMapping.getDescriptor();

        // die beinhaltende Klasse
        final Class<?> javaClazz = descriptor.getJavaClass(); // FunctionalGroup

        // wenn eine JoinTable via annotation deklariert mit wurde, und 'name' nicht leer ist machen wir nichts
		try {
			final Field attributeField = javaClazz.getDeclaredField(attributeName);
			final ManyToMany manyToManyAnnotation = attributeField.getAnnotation(ManyToMany.class);
			final String mappedBy = manyToManyAnnotation.mappedBy();
			if (!mappedBy.trim().isEmpty()) {

				// skip processing
				return;
			}

            if (attributeField.isAnnotationPresent(JoinTable.class)) {
                final JoinTable joinTableAnnotation = attributeField.getAnnotation(JoinTable.class);
                final String name = joinTableAnnotation.name();
                if (!name.trim().isEmpty()) {

                    // skip processing here
                    return;
                }
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }

        final RelationTableMechanism mechanism = databaseMapping.getRelationTableMechanism();

        // relationTable defines name, qualifiedname
        final DatabaseTable databaseTable = mechanism.getRelationTable();

        final String lhs = NameUtils.camelCaseToUnderscore(javaClazz.getSimpleName());

        final String rhs = NameUtils.camelCaseToUnderscore(referenceClazz.getSimpleName());

        final String newTableName = new StringBuilder().append(lhs).append("_").append(rhs).toString();

        databaseTable.setName(newTableName);

        // wenn deklariert in @JoinTable(name)
        final String databaseTableName = databaseTable.getName();

        // sourceKeyFields beinhaltet databasefield des SourceIdDatabasefield zum beispiel : deployment_set.ds_id
        Vector<DatabaseField> sourcekeyFields = mechanism.getSourceKeyFields();

        for (DatabaseField field : sourcekeyFields) {
            String fieldName = field.getName();
            field.setName(fieldName.toLowerCase());
        }

        // sourceRelationKeyField zum beispiel : deploymentset_projects.DeploymentSet_ID
        Vector<DatabaseField> sourceRelationKeyFields = mechanism.getSourceRelationKeyFields();

        for (DatabaseField field : sourceRelationKeyFields) {
            String fieldName = field.getName();
            if (fieldName.endsWith("_ID")) {
                fieldName = fieldName.substring(0, fieldName.indexOf("_ID"));
            }

            String iconized = NameUtils.iconizeTableName(newTableName);

            String newFieldName = new StringBuilder().append(iconized).append("_")
                                                     .append(NameUtils.camelCaseToUnderscore(fieldName)).append("_")
                                                     .append("id").toString();
            field.setName(newFieldName);
        }

        // targetKeyField zum beispiel : project.p_id
        Vector<DatabaseField> targetKeyFields = mechanism.getTargetKeyFields();

        for (DatabaseField field : targetKeyFields) {
            String fieldName = field.getName();
            field.setName(fieldName.toLowerCase());
        }

        // targetRelationKeyField zum Beispiel deploymentset_projects.projects_ID
        Vector<DatabaseField> targetRelationKeyField = mechanism.getTargetRelationKeyFields();

        for (DatabaseField field : targetRelationKeyField) {
            String fieldName = field.getName();
            if (fieldName.endsWith("_ID")) {
                fieldName = fieldName.substring(0, fieldName.indexOf("_ID"));
            }

            String iconized = NameUtils.iconizeTableName(newTableName);
            String newFieldName = new StringBuilder().append(iconized).append("_")
                                                     .append(NameUtils.camelCaseToUnderscore(
                                                             referenceClazz.getSimpleName())).append("_").append("id")
                                                     .toString();
            field.setName(newFieldName);
        }

    }

}
