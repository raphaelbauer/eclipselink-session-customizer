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

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.sessions.Session;

/**
 * Defines a ColumnNameCustomizer. A {@link ColumnNameCustomizer} does only one thing, customize the column-names for an
 * specific {@link DatabaseMapping}.
 *
 * @param   <T>
 *
 * @author  jbellmann
 */
public interface ColumnNameCustomizer<T extends DatabaseMapping> {

    void customizeColumnName(String tableName, T databaseMapping, Session session);

    Class<T> supportedDatabaseMapping();

}
