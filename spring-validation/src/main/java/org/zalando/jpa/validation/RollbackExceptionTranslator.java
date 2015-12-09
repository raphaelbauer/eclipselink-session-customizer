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
package org.zalando.jpa.validation;

import javax.persistence.RollbackException;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.zalando.jpa.validation.ReportingConstraintViolationException;

/**
 * Translates a {@link RollbackException}'s cause into {@link ReportingConstraintViolationException} if cause was an
 * instance of {@link ConstraintViolationException}.
 *
 * @author  <a href="mailto:marcel.wieczorek@zalando.de" title="Marcel Wieczorek">mwieczorek</a>
 */
public class RollbackExceptionTranslator implements PersistenceExceptionTranslator {

    /* (non-Javadoc)
     * @see org.springframework.dao.support.PersistenceExceptionTranslator#translateExceptionIfPossible(java.lang.RuntimeException)
     */
    @Override
    public DataAccessException translateExceptionIfPossible(final RuntimeException ex) {
        if (ex instanceof RollbackException) {
            final Throwable cause = ex.getCause();
            if (cause instanceof ConstraintViolationException) {
                return new ReportingConstraintViolationException((ConstraintViolationException) cause);
            }
        }

        return null;
    }
}
