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
package org.zalando.jpa.eclipselink.customizer.databasemapping.support;

import org.eclipse.persistence.mappings.ManyToOneMapping;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.LogSupport;

public class ManyToOneMappingLogger extends LogSupport implements DatabaseMappingLogger<ManyToOneMapping> {

    @Override
    public void logDatabaseMapping(final ManyToOneMapping databaseMapping, final Session session) {

        logFine(session, "\tmapping.attributeName : {0}", databaseMapping.getAttributeName());
        logFine(session, "\tmapping.attributeClassification: {0}", databaseMapping.getAttributeClassification());
    }

    @Override
    public Class<ManyToOneMapping> getMappingType() {
        return ManyToOneMapping.class;
    }
}
