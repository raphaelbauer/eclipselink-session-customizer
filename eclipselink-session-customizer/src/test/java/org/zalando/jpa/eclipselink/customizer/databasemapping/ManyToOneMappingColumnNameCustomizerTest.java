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

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;

import java.util.Vector;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.mappings.ManyToOneMapping;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.zalando.jpa.eclipselink.AttributeHolderBean;
import org.zalando.jpa.eclipselink.MockSessionCreator;

public class ManyToOneMappingColumnNameCustomizerTest {

    ManyToOneMappingColumnNameCustomizer customizer = new ManyToOneMappingColumnNameCustomizer();

    ManyToOneMapping mapping = Mockito.mock(ManyToOneMapping.class);
    DatabaseField dataBaseField = Mockito.mock(DatabaseField.class);

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Before
    public void setUp() {
        Mockito.reset(mapping, dataBaseField);

        Vector v = new Vector();
        v.add(dataBaseField);
        Mockito.when(mapping.getForeignKeyFields()).thenReturn(v);
        Mockito.when(mapping.getSourceToTargetKeyFieldAssociations()).thenReturn(new Vector());

        ClassDescriptor classDescriptor = new ClassDescriptor();
        classDescriptor.setJavaClass(AttributeHolderBean.class);
        Mockito.when(mapping.getDescriptor()).thenReturn(classDescriptor);
        Mockito.when(mapping.getAttributeName()).thenReturn("");
    }

    /**
     * {@link DatabaseField#setName(String)} should be invoked because the needed prefix is missing yet.
     */
    @Ignore
    @Test
    public void testCustomizeWhenFieldHasNoPrefixYet() {

        // does not have the needed prefix
        Mockito.when(dataBaseField.getName()).thenReturn("order_status");
        Mockito.when(mapping.getAttributeName()).thenReturn("orderStatus");
        ClassDescriptor descriptor = Mockito.mock(ClassDescriptor.class);
        Mockito.when(mapping.getDescriptor()).thenReturn(descriptor);
        Mockito.when(descriptor.getJavaClass()).thenReturn(AttributeHolderBean.class);

        //
        customizer.customizeColumnName("purchase_order_head", mapping, MockSessionCreator.create());

        // invocation was needed, not prefixed yet
        Mockito.verify(dataBaseField, times(1)).setName(eq("poh_order_status"));
    }

    /**
     * {@link DatabaseField#setName(String)} should not be invoked, because the field-name is prefixed correct.
     */
//    @Ignore
    @Test
    public void testCustomizeWhenFieldStartsWithPrefix() {

        // prefix the fieldname
        Mockito.when(dataBaseField.getName()).thenReturn("poh_order_status_id");
        Mockito.when(mapping.getAttributeName()).thenReturn("orderStatusId");
        ClassDescriptor descriptor = Mockito.mock(ClassDescriptor.class);
        Mockito.when(mapping.getDescriptor()).thenReturn(descriptor);
        Mockito.when(descriptor.getJavaClass()).thenReturn(AttributeHolderBean.class);
        

        //
        customizer.customizeColumnName("purchase_order_head", mapping, MockSessionCreator.create());

        // no invocation needed, fieldname is prefixed
        Mockito.verify(dataBaseField, times(0)).setName(anyString());
    }

}
