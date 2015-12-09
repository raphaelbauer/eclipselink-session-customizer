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
/**
 * Marcel Wieczorek
 * Zalando GmbH
 * Nov 8, 2012 11:52:29 AM
 */
package org.zalando.jpa.validation;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;

/**
 * @author  <a href="mailto:marcel.wieczorek@zalando.de" title="Marcel Wieczorek">mwieczorek</a>
 */
public class ConstraintViolationExceptionTranslator implements PersistenceExceptionTranslator {

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.dao.support.PersistenceExceptionTranslator#
     * translateExceptionIfPossible(java.lang.RuntimeException)
     */
    @Override
    public DataAccessException translateExceptionIfPossible(final RuntimeException ex) {
        if (ex instanceof ConstraintViolationException) {
            return new ReportingConstraintViolationException((ConstraintViolationException) ex);
        }

        return null;
    }

}
