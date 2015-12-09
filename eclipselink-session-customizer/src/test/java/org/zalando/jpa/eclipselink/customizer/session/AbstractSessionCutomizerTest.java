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

import java.util.Map;
import java.util.Vector;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.internal.sessions.DatabaseSessionImpl;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.DirectToFieldMapping;
import org.eclipse.persistence.sessions.Login;
import org.eclipse.persistence.sessions.Project;
import org.eclipse.persistence.sessions.Session;

import org.mockito.Mockito;
import org.zalando.jpa.eclipselink.AttributeHolderBean;
import org.zalando.jpa.eclipselink.Slf4jSessionLog;

import com.google.common.collect.Maps;

/**
 * @author  jbellmann
 */
public abstract class AbstractSessionCutomizerTest {

    public void testCustomizer() throws Exception {
        SessionCustomizer defaultSessionCustomizer = getSessionCustomizer();

        DatabaseField field = new DatabaseField();

// field.se

        DirectToFieldMapping mapping = new DirectToFieldMapping();
        mapping.setAttributeName("brandCode");
        mapping.setField(field);
        mapping.setAttributeClassification(String.class);

        ClassDescriptor classDescriptor = new ClassDescriptor();
        classDescriptor.setJavaClass(AttributeHolderBean.class);
        mapping.setDescriptor(classDescriptor);

        Vector<DatabaseMapping> mappings = new Vector<>();
        mappings.add(mapping);

        ClassDescriptor descriptor = new ClassDescriptor();
        descriptor.setJavaClass(AttributeHolderBean.class);
        descriptor.setMappings(mappings);
        descriptor.setTableName("purchase_order_head");

        Map<Class, ClassDescriptor> result = Maps.newHashMap();

        result.put(Order.class, descriptor);

        Login login = Mockito.mock(Login.class);
        Project project = new Project();
        project.setDescriptors(result);
        project.setDatasourceLogin(login);

        Session session = new DatabaseSessionImpl(project);
        session.setSessionLog(new Slf4jSessionLog());

        //
        defaultSessionCustomizer.customize(session);
    }

    static final class Order { }

    abstract SessionCustomizer getSessionCustomizer();

}
