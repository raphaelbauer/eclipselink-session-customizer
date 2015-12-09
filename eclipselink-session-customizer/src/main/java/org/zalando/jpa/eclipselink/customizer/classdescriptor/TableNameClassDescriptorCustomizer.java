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
package org.zalando.jpa.eclipselink.customizer.classdescriptor;

import javax.persistence.Table;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.LogSupport;
import org.zalando.jpa.eclipselink.customizer.NameUtils;

/**
 * Customize the name of the Table if not explicit set by the author.
 *
 * @author  maciej
 * @author  jbellmann
 */
public class TableNameClassDescriptorCustomizer implements ClassDescriptorCustomizer {

    @Override
    public void customize(final ClassDescriptor clazzDescriptor, final Session session) {

        final Class<?> clazz = clazzDescriptor.getJavaClass();

        // we want to respect Annotations, right?
        if (clazz.isAnnotationPresent(Table.class)) {

            final Table tableAnnotation = clazz.getAnnotation(Table.class);

            // is never null, defaults to empty string
            final String nameValue = tableAnnotation.name();

            if (nameValue.trim().isEmpty()) {

                iconizeTableName(clazzDescriptor, session);
            }

        } else {

            iconizeTableName(clazzDescriptor, session);
        }
    }

    /**
     * @param  clazzDescriptor
     */
    protected void iconizeTableName(final ClassDescriptor clazzDescriptor, final Session session) {
        final String alias = clazzDescriptor.getAlias();

        final String tableName = NameUtils.camelCaseToUnderscore(alias);

        LogSupport.logFine(session, "Set Tablename to {0}", tableName);
        clazzDescriptor.setTableName(tableName);
    }

}
