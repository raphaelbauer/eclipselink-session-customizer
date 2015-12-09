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
import java.util.Map;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.LogSupport;
import org.zalando.jpa.eclipselink.customizer.classdescriptor.ClassDescriptorCustomizer;
import org.zalando.jpa.eclipselink.customizer.classdescriptor.DefaultClassDescriptorCustomizer;
import org.zalando.jpa.eclipselink.customizer.classdescriptor.TableNameClassDescriptorCustomizer;
import org.zalando.jpa.eclipselink.customizer.databasemapping.AggregateObjectMappingColumnNameCustomizer;
import org.zalando.jpa.eclipselink.customizer.databasemapping.ColumnNameCustomizer;
import org.zalando.jpa.eclipselink.customizer.databasemapping.ConverterCustomizer;
import org.zalando.jpa.eclipselink.customizer.databasemapping.DirectToFieldMappingColumnNameCustomizer;
import org.zalando.jpa.eclipselink.customizer.databasemapping.ManyToManyMappingCustomizer;
import org.zalando.jpa.eclipselink.customizer.databasemapping.ManyToOneMappingColumnNameCustomizer;
import org.zalando.jpa.eclipselink.customizer.databasemapping.OneToManyMappingColumnNameCustomizer;
import org.zalando.jpa.eclipselink.customizer.databasemapping.OneToOneMappingColumnNameCustomizer;

import com.google.common.collect.Lists;

/**
 * Base for {@link SessionCustomizer} implementations.
 *
 * @author  jbellmann
 */
public abstract class AbstractSessionCustomizer extends LogSupport implements SessionCustomizer {

    /**
     * Customize the {@link Session}.
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void customize(final Session session) throws Exception {
        logInfo(session, CUS_SESSION_START);

        logSessionProperties(session);

        logInfo(session, "Build ClassDescriptorCustomizer ...");

        final ClassDescriptorCustomizer classDescriptorCustomizer = getClassDescriptorCustomizer();

        logInfo(session, "ClassDescriptorCustomizer created.");

        // process customization on all clazzDescriptors
        Map<Class, ClassDescriptor> clazzDescriptors = session.getDescriptors();
        for (Map.Entry<Class, ClassDescriptor> descriptorEntry : clazzDescriptors.entrySet()) {

            classDescriptorCustomizer.customize(descriptorEntry.getValue(), session);
        }

        logInfo(session, CUS_SESSION_END);
    }

    protected void logSessionProperties(final Session session) {
        logFine(session, SESSION_PROPS);
        for (Map.Entry<Object, Object> entry : session.getProperties().entrySet()) {
            logFine(session, KEY_VALUE, entry.getKey().toString(), entry.getValue().toString());
        }
    }

    /**
     * Returns the final {@link ClassDescriptorCustomizer} used for customizing the {@link Session}.
     *
     * @return  an {@link ClassDescriptorCustomizer}
     */
    protected ClassDescriptorCustomizer getClassDescriptorCustomizer() {

        // make sure the TableNameClassDescriptorCustomizer is the first one
        ClassDescriptorCustomizer ccdc = CompositeClassDescriptorCustomizer.build(
                new TableNameClassDescriptorCustomizer(), getDefaultClassDescriptorCustomizer());
        return ccdc;
    }

    /**
     * Builds an {@link ClassDescriptorCustomizer} with return values of {@link #getDefaultColumnNameCustomizers()} and
     * {@link #getDefaultConverterCustomizer()}.
     *
     * @return  an {@link ClassDescriptorCustomizer}
     */
    protected ClassDescriptorCustomizer getDefaultClassDescriptorCustomizer() {
        DefaultClassDescriptorCustomizer d = new DefaultClassDescriptorCustomizer();
        d.registerColumnNameCustomizer(getDefaultColumnNameCustomizers());
        d.registerConverterCustomizer(getDefaultConverterCustomizer());
        return d;
    }

    /**
     * Returns a list of default {@link ColumnNameCustomizer} implementations.
     *
     * @return  list of {@link ColumnNameCustomizer}s
     */
    protected List<ColumnNameCustomizer> getDefaultColumnNameCustomizers() {
        List<ColumnNameCustomizer> columnNameCustomizer = Lists.newLinkedList();

        columnNameCustomizer.add(new DirectToFieldMappingColumnNameCustomizer());

        columnNameCustomizer.add(new OneToManyMappingColumnNameCustomizer());
        columnNameCustomizer.add(new OneToOneMappingColumnNameCustomizer());

        columnNameCustomizer.add(new ManyToOneMappingColumnNameCustomizer());
        columnNameCustomizer.add(new ManyToManyMappingCustomizer());
        columnNameCustomizer.add(new AggregateObjectMappingColumnNameCustomizer());

        return columnNameCustomizer;
    }

    /**
     * Returns an empty list of {@link ConverterCustomizer}.
     *
     * @return  list of {@link ConverterCustomizer}s.
     */
    protected List<ConverterCustomizer> getDefaultConverterCustomizer() {
        return Lists.newArrayList();
    }
}
