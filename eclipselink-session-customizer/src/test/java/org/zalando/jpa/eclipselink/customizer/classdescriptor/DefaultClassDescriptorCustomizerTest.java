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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.Embeddable;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.sessions.Session;

import org.junit.Before;
import org.junit.Test;
import org.zalando.jpa.eclipselink.Slf4jSessionLog;

/**
 * @author  jbellmann
 */
public class DefaultClassDescriptorCustomizerTest {

    private Session session = mock(Session.class);

    private ClassDescriptor clazzDescriptor;

    private DefaultClassDescriptorCustomizer customizer;

    private SessionLog sessionLog = new Slf4jSessionLog();

    @Before
    public void setUp() {

        clazzDescriptor = mock(ClassDescriptor.class);
        customizer = new DefaultClassDescriptorCustomizer();
        when(session.getSessionLog()).thenReturn(sessionLog);

    }

    /**
     * TODO. This is an open discussion yet. How to handle {@link Embeddable} annotated classes in the
     * {@link DefaultClassDescriptorCustomizer}? At the moment we are ignoring/skipping them from customization.
     */
    @Test
    public void testWithEmbeddableClasses() {

        when(clazzDescriptor.getJavaClass()).thenReturn(EmbeddableTestEntity.class);
        when(clazzDescriptor.getJavaClassName()).thenReturn(EmbeddableTestEntity.class.getSimpleName());

        customizer.customize(clazzDescriptor, session);

        verify(clazzDescriptor, never()).getMappings();
    }

    @Embeddable
    static class EmbeddableTestEntity {

        private String field;

        public String getField() {
            return field;
        }

        public void setField(final String field) {
            this.field = field;
        }

    }
}
