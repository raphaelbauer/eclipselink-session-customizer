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

import static org.mockito.Matchers.eq;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.DirectToFieldMapping;

import org.junit.Test;

import org.mockito.Mockito;
import org.zalando.jpa.eclipselink.AttributeHolderBean;
import org.zalando.jpa.eclipselink.MockSessionCreator;

/**
 * @author  jbellmann
 */
public class DirectToFieldMappingColumnNameCustomizerTest {

    DirectToFieldMappingColumnNameCustomizer customizer = new DirectToFieldMappingColumnNameCustomizer();

    @Test
    public void testCustomization() {
        DirectToFieldMapping mapping = Mockito.mock(DirectToFieldMapping.class);
        DatabaseField dataBaseField = Mockito.mock(DatabaseField.class);
        ClassDescriptor classDescriptor = new ClassDescriptor();
        classDescriptor.setJavaClass(AttributeHolderBean.class);
        Mockito.when(mapping.getField()).thenReturn(dataBaseField);
        Mockito.when(mapping.getAttributeName()).thenReturn("orderStatus");
        Mockito.when(mapping.getDescriptor()).thenReturn(classDescriptor);

        // invoke
        customizer.customizeColumnName("purchase_order_head", mapping, MockSessionCreator.create());

        //
        Mockito.verify(dataBaseField).setName(eq("poh_order_status"));
    }

    @Test
    public void booleanCustomizationStartsWithIsPrefix() {
        DirectToFieldMapping mapping = Mockito.mock(DirectToFieldMapping.class);
        DatabaseField dataBaseField = Mockito.mock(DatabaseField.class);
        ClassDescriptor classDescriptor = new ClassDescriptor();
        classDescriptor.setJavaClass(AttributeHolderBean.class);
        Mockito.when(mapping.getField()).thenReturn(dataBaseField);
        Mockito.when(mapping.getAttributeName()).thenReturn("ordered");
        Mockito.when(mapping.getAttributeClassification()).thenReturn(Boolean.class);
        Mockito.when(mapping.getDescriptor()).thenReturn(classDescriptor);

        // invoke
        customizer.customizeColumnName("purchase_order_head", mapping, MockSessionCreator.create());

        //
        Mockito.verify(dataBaseField).setName(eq("poh_is_ordered"));
    }
}
