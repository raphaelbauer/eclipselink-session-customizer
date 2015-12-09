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

import static java.util.Arrays.asList;

import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.customizer.classdescriptor.ClassDescriptorCustomizer;

/**
 * To combine multiple {@link ClassDescriptorCustomizer}s.
 *
 * @author  jbellmann
 */
final class CompositeClassDescriptorCustomizer implements ClassDescriptorCustomizer {

    protected List<ClassDescriptorCustomizer> customizers = new LinkedList<ClassDescriptorCustomizer>();

    /**
     * Hide constructor.
     */
    protected CompositeClassDescriptorCustomizer() { }

    /**
     * Runs every added {@link ClassDescriptorCustomizer} for an {@link ClassDescriptor} with provided {@link Session}.
     */
    @Override
    public void customize(final ClassDescriptor clazzDescriptor, final Session session) {
        for (ClassDescriptorCustomizer c : customizers) {
            c.customize(clazzDescriptor, session);
        }
    }

    /**
     * Creates an {@link CompositeClassDescriptorCustomizer} from the assigned array of
     * {@link ClassDescriptorCustomizer}.
     *
     * @param   classDescriptorCustomizers
     *
     * @return
     */
    public static ClassDescriptorCustomizer build(final ClassDescriptorCustomizer... classDescriptorCustomizers) {
        CompositeClassDescriptorCustomizer composite = new CompositeClassDescriptorCustomizer();
        composite.customizers.addAll(newArrayList(filter(asList(classDescriptorCustomizers), notNull())));
        return composite;
    }

    /**
     * Creates an {@link CompositeClassDescriptorCustomizer} from the assigned list of {@link ClassDescriptorCustomizer}.
     *
     * @param   classDescriptorCustomizers
     *
     * @return
     */
    public static ClassDescriptorCustomizer build(final List<ClassDescriptorCustomizer> classDescriptorCustomizers) {
        return build(classDescriptorCustomizers.toArray(new ClassDescriptorCustomizer[0]));
    }

}
