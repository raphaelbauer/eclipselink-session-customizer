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

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import org.springframework.util.Assert;

/**
 * Caution! This is not a real {@link PersistenceExceptionTranslator}.<br/>
 * It only logs {@link ConstraintViolationException}.<br/>
 * Change output with a specific {@link ConstraintViolationPrinter}-implementation. Default is
 * {@link LogConstraintViolationPrinter}.
 *
 * @author  jbellmann
 * @see     ConstraintViolationPrinter
 */
public class ConstraintViolationExceptionLogger implements PersistenceExceptionTranslator {

    private static final Logger LOG = LoggerFactory.getLogger(ConstraintViolationExceptionLogger.class);

    private ConstraintViolationPrinter violationPrinter = new LogConstraintViolationPrinter();

    @Override
    public DataAccessException translateExceptionIfPossible(final RuntimeException ex) {
        Throwable[] suppressedExceptions = ex.getSuppressed();
        for (Throwable t : suppressedExceptions) {
            LOG.warn("SUPPRESSED EXCEPTION --> : {} ", t.getClass().getName());
        }

        if (ex instanceof ConstraintViolationException) {
            violationPrinter.printValidationErrors((ConstraintViolationException) ex);
        }

        return null;
    }

    public void setViolationPrinter(final ConstraintViolationPrinter violationPrinter) {
        Assert.notNull(violationPrinter, "'violationPrinter' should never be null");
        this.violationPrinter = violationPrinter;
    }
}
