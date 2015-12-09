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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.Table;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.sessions.Session;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;
import org.zalando.jpa.eclipselink.Slf4jSessionLog;
import org.zalando.jpa.eclipselink.customizer.NameUtils;

/**
 * Test different cases of annoated and not annotated Entities for TableNameCustomization.
 *
 * @author  jbellmann
 */
public class TableNameClassDescriptorCustomizerTest {

    private Session session = mock(Session.class);
    private ClassDescriptor clazzDescriptor;

    private TableNameClassDescriptorCustomizer customizer;

    private SessionLog sessionLog = new Slf4jSessionLog();

    @Before
    public void setUp() {
        clazzDescriptor = mock(ClassDescriptor.class);
        customizer = new TableNameClassDescriptorCustomizer();
        when(session.getSessionLog()).thenReturn(sessionLog);
    }

    @Test
    public void tableNameCustomizationWithAnnotatedClazz() {

        when(clazzDescriptor.getJavaClass()).thenReturn(AnnotatedEntity.class);
        when(clazzDescriptor.getJavaClassName()).thenReturn(AnnotatedEntity.class.getSimpleName());

        customizer.customize(clazzDescriptor, session);

        verify(clazzDescriptor, never()).setTableName(Mockito.anyString());
    }

    @Test
    public void tableNameCustomizationWithAnnotatedClazzSchemaOnly() {

        when(clazzDescriptor.getJavaClass()).thenReturn(OnlySchemaDefinedEntity.class);
        when(clazzDescriptor.getJavaClassName()).thenReturn(OnlySchemaDefinedEntity.class.getSimpleName());
        when(clazzDescriptor.getAlias()).thenReturn(OnlySchemaDefinedEntity.class.getSimpleName());

        String iconizedTableName = NameUtils.camelCaseToUnderscore(OnlySchemaDefinedEntity.class.getSimpleName());

        customizer.customize(clazzDescriptor, session);

        verify(clazzDescriptor, times(1)).setTableName(Mockito.eq(iconizedTableName));
    }

    @Test
    public void tableNameCustomizationWhenNoAnnotationPresent() {

        when(clazzDescriptor.getJavaClass()).thenReturn(NotAnnotatedEntity.class);
        when(clazzDescriptor.getJavaClassName()).thenReturn(NotAnnotatedEntity.class.getSimpleName());
        when(clazzDescriptor.getAlias()).thenReturn(NotAnnotatedEntity.class.getSimpleName());

        String iconizedTableName = NameUtils.camelCaseToUnderscore(NotAnnotatedEntity.class.getSimpleName());

        customizer.customize(clazzDescriptor, session);

        verify(clazzDescriptor, times(1)).setTableName(Mockito.eq(iconizedTableName));

    }

    @Table(name = "annotated_entity")
    static class AnnotatedEntity { }

    @Table(schema = "zzj_data")
    static class OnlySchemaDefinedEntity { }

    // NO Annotatio present, should be n_a_e
    static class NotAnnotatedEntity { }
}
