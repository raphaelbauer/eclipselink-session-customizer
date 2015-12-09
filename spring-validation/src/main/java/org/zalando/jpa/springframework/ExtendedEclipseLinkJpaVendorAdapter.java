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
package org.zalando.jpa.springframework;

import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

/**
 * This {@link EclipseLinkJpaVendorAdapter} is intended to use our {@link ExtendedEclipseLinkJpaDialect}.<br/>
 * At time of writing we do not find a 'setter' for the 'jpaDialect'.
 *
 * @author  jbellmann
 */
public class ExtendedEclipseLinkJpaVendorAdapter extends EclipseLinkJpaVendorAdapter {

    private EclipseLinkJpaDialect jpaDialect = new ExtendedEclipseLinkJpaDialect();

    /**
     * Returns our modified {@link JpaDialect}.
     *
     * @see  {@link ExtendedEclipseLinkJpaDialect}
     */
    @Override
    public EclipseLinkJpaDialect getJpaDialect() {
        return this.jpaDialect;
    }

}
