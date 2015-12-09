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
package org.zalando.jpa.another.project;

import org.zalando.jpa.eclipselink.customizer.session.AbstractSessionCustomizer;

/**
 * @author  jbellmann
 */
public class ProjectSessionCustomizer extends AbstractSessionCustomizer {

// private final ClassDescriptorCustomizer clazzDescriptorCustomizer;

    public ProjectSessionCustomizer() {
        super();

// final ClassDescriptorCustomizer zalandoDefaults = defaultColumnNameClassDescriptorCustomizer();
//
// clazzDescriptorCustomizer = builder().with(zalandoDefaults).build();
    }

// @Override
// public ClassDescriptorCustomizer getClassDescriptorCustomizer() {
// return clazzDescriptorCustomizer;
// }

}
