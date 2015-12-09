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

import javax.persistence.RollbackException;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataAccessException;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;

import org.springframework.util.Assert;
import org.zalando.jpa.validation.ConstraintViolationPrinter;
import org.zalando.jpa.validation.LogConstraintViolationPrinter;

/**
 * This extended version for {@link EclipseLinkJpaDialect} is intended to only log {@link ConstraintViolationException}s.
 *
 * @author  jbellmann
 */
public class ExtendedEclipseLinkJpaDialect extends EclipseLinkJpaDialect {

    private static final long serialVersionUID = 1L;

    private ConstraintViolationPrinter violationPrinter = new LogConstraintViolationPrinter();

    /**
     * This method will be called when the {@link JpaTransactionManager} tries to translate the 'cause' for the
     * {@link RollbackException}.<br/>
     * We use this only for logging the message in a more readable manner.
     *
     * @see  JpaTransactionManager#doCommit(org.springframework.transaction.TransactionStatus)
     */
    @Override
    public DataAccessException translateExceptionIfPossible(final RuntimeException ex) {
        DataAccessException translated = super.translateExceptionIfPossible(ex);
        if (ex instanceof ConstraintViolationException) {
            violationPrinter.printValidationErrors((ConstraintViolationException) ex);
        }

        return translated;
    }

    public ConstraintViolationPrinter getViolationPrinter() {
        return violationPrinter;
    }

    public void setViolationPrinter(final ConstraintViolationPrinter constraintViolationPrinter) {
        Assert.notNull(constraintViolationPrinter, "ConstraintViolationPrinter should never be null");
        this.violationPrinter = constraintViolationPrinter;
    }

}
