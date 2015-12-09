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
package org.zalando.jpa.eclipselink;

/**
 * Defines PersistenceUnitProperties to be used.
 *
 * @author  jbellmann
 */
public final class PersistenceUnitProperties {

    public static final String ZALANDO_ECLIPSELINK_CHANGE_TRACKER_NAME = "zalando.eclipselink.change-tracker-name";

    public static final String ZALANDO_ECLIPSELINK_PARTITION_POLICY_FACTORY_NAME =
        "zalando.eclipselink.partition-policy-factory-name";

}
