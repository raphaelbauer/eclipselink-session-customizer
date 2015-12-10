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

import java.util.Map;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.mappings.ManyToOneMapping;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.LogSupport;
import org.zalando.jpa.eclipselink.customizer.databasemapping.support.DatabaseMappingLogger;
import org.zalando.jpa.eclipselink.customizer.databasemapping.support.DirectToFieldMappingLogger;
import org.zalando.jpa.eclipselink.customizer.databasemapping.support.ManyToOneMappingLogger;

import com.google.common.collect.Maps;

/**
 * @param   <T>
 *
 * @author  jbellmann
 */
public abstract class AbstractColumnNameCustomizer<T extends DatabaseMapping> extends LogSupport
    implements ColumnNameCustomizer<T> {

    private Class<T> supportedMappingType;

    private Map<Class<? extends DatabaseMapping>, DatabaseMappingLogger<?>> loggerMap = Maps.newHashMap();

    protected AbstractColumnNameCustomizer(final Class<T> supportedMappingType) {
        this.supportedMappingType = supportedMappingType;
        loggerMap.put(DirectToFieldMapping.class, new DirectToFieldMappingLogger());
        loggerMap.put(ManyToOneMapping.class, new ManyToOneMappingLogger());
    }

    @Override
    public Class<T> supportedDatabaseMapping() {
        return this.supportedMappingType;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void logDatabaseMapping(final DatabaseMapping databaseMapping, final Session session) {
        DatabaseMappingLogger logger = loggerMap.get(databaseMapping.getClass());
        if (logger != null) {
            logger.logDatabaseMapping(databaseMapping, session);
        }
    }

//    protected EntityFieldInspector<? extends Annotation> getFieldInspector(final DatabaseMapping databaseMapping) {
//        final String attributeName = databaseMapping.getAttributeName();
//        final Class<?> entityClass = databaseMapping.getDescriptor().getJavaClass();
//
//        
//        Set<Field> fieldsWithName = ReflectionUtils.getAllFields(entityClass, ReflectionUtils.withName(attributeName));
//        
//        final Field field = Iterables.get(fieldsWithName, 0);
////        final Field field = ReflectionUtils.findField(entityClass, attributeName);
//        return new ColumnFieldInspector(field);
//    }

}
