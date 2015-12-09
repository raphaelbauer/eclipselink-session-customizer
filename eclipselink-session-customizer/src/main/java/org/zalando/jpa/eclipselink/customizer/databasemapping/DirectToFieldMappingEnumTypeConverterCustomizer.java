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

import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.mappings.converters.EnumTypeConverter;
import org.eclipse.persistence.sessions.Session;

/**
 * @author  jbellmann
 */
public class DirectToFieldMappingEnumTypeConverterCustomizer extends AbstractConverterCustomizer<DirectToFieldMapping> {

    public DirectToFieldMappingEnumTypeConverterCustomizer() {
        super(DirectToFieldMapping.class);
    }

    @Override
    public void customizeConverter(final DirectToFieldMapping databaseMapping, final Session session) {

        if (hasEnumTypeConverter(databaseMapping)) {

            final EnumTypeConverter eclipseConverter = (EnumTypeConverter) databaseMapping.getConverter();
            final Class enumClazz = eclipseConverter.getEnumClass();

            logFine(session, "Set enum-converter to field {0} with class {1}", databaseMapping.getField().getName(),
                enumClazz.getName());

            databaseMapping.setConverter(
                new org.zalando.jpa.eclipselink.customizer.databasemapping.support.EnumTypeConverter(enumClazz,
                    databaseMapping.getField().getColumnDefinition()));

        }

    }

    protected boolean hasEnumTypeConverter(final DirectToFieldMapping databaseMapping) {
        return databaseMapping.getConverter() != null
                && EnumTypeConverter.class.equals(databaseMapping.getConverter().getClass());

    }

}
