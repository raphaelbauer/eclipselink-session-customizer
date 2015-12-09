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

import static org.mockito.Mockito.only;

import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DirectToFieldMapping;

import org.junit.Ignore;
import org.junit.Test;

import org.mockito.Mockito;
import org.zalando.jpa.eclipselink.MockSessionCreator;

/**
 * @author  jbellmann
 */
@Ignore
public class NoOpConverterCustomizerTest {

    @Test
    public void testNoOperation() {
        NoOpConverterCustomizer noOpCustomizer = new NoOpConverterCustomizer();
        DirectToFieldMapping mapping = Mockito.spy(new DirectToFieldMapping());
        mapping.setAttributeName("brandCode");
        mapping.setField(new DatabaseField());
        mapping.setAttributeClassification(String.class);
// Mockito.spy(mapping);

        noOpCustomizer.customizeConverter(mapping, MockSessionCreator.create());

        // we only use getAttributeName in the logging statement, make sure this is only call to mapping
        Mockito.verify(mapping.getAttributeName(), only());
    }

}
