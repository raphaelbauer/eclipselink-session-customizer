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

import org.junit.Assert;
import org.junit.Test;
import org.zalando.jpa.eclipselink.customizer.classdescriptor.ChangePolicyClassDescriptorCustomizer;
import org.zalando.jpa.eclipselink.customizer.classdescriptor.ClassDescriptorCustomizer;

/**
 * @author  jbellmann
 */
public class BuilderTest {

    @Test
    public void withBuilder() {
        AbstractSessionCustomizer sessionCustomizer = new BuilderSessionCustomizer();
        Assert.assertNotNull(sessionCustomizer);

        ClassDescriptorCustomizer c = sessionCustomizer.getClassDescriptorCustomizer();
        Assert.assertNotNull(c);
        Assert.assertTrue(CompositeClassDescriptorCustomizer.class.isAssignableFrom(c.getClass()));
    }

    class BuilderSessionCustomizer extends AbstractSessionCustomizer {

        @Override
        public ClassDescriptorCustomizer getClassDescriptorCustomizer() {
            ClassDescriptorCustomizer ccdc = CompositeClassDescriptorCustomizer.build(super
                        .getClassDescriptorCustomizer(), new ChangePolicyClassDescriptorCustomizer());
            return ccdc;
// return builder().with(new DefaultClassDescriptorCustomizer())
// .with(new ChangePolicyClassDescriptorCustomizer()).build();
        }

    }

}
