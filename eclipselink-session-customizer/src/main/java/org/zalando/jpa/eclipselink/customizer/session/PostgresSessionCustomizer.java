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
package org.zalando.jpa.eclipselink.customizer.session;

import java.util.List;

import org.zalando.jpa.eclipselink.customizer.databasemapping.ConverterCustomizer;
import org.zalando.jpa.eclipselink.customizer.databasemapping.DirectToFieldMappingEnumTypeConverterCustomizer;

/**
 * @author      jbellmann
 * @deprecated  will be deleted next time
 */
@Deprecated
public class PostgresSessionCustomizer extends AbstractSessionCustomizer {

    public PostgresSessionCustomizer() {
        super();
    }

    @Override
    protected List<ConverterCustomizer> getDefaultConverterCustomizer() {
        List<ConverterCustomizer> converterCustomizer = super.getDefaultConverterCustomizer();
        converterCustomizer.add(new DirectToFieldMappingEnumTypeConverterCustomizer());

        return converterCustomizer;
    }

}
