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
package org.zalando.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.zalando.jpa.validation.ConstraintViolationPrinter;
import org.zalando.jpa.validation.SystemErrConstraintViolationPrinter;

/**
 * Generic, configurable {@link SimpleBeanValidator} for testing. Validates against {@link Default}-group as a default.
 * {@link ConstraintViolationPrinter} can be replaced by your own implementation. Default prints to {@link System#err}.
 *
 * @author  jbellmann
 */
@Component
public class SimpleBeanValidator {

    private Validator validator;

    @Autowired
    public SimpleBeanValidator(final Validator validator) {
        Assert.notNull(validator, "Validator has to be not null");
        this.validator = validator;
    }

    private Class<?>[] groups = new Class<?>[] {Default.class};

    private ConstraintViolationPrinter printer = new SystemErrConstraintViolationPrinter();

    /**
     * Validates a Bean against the Default-Validation-Group. Prints validation error messages by default.
     *
     * @param  entity  Bean to validate
     */
    public <T> void validate(final T entity) {
        validate(entity, true, groups);
    }

    /**
     * Validates a Bean against specified validation groups. Prints validation error messages by default.
     *
     * @param  entity  Bean to validate
     * @param  groups  specified validation groups
     */
    public <T> void validate(final T entity, final Class<?>... groups) {
        validate(entity, true, groups);
    }

    /**
     * Set a printer for this Validator.
     *
     * @param  constraintViolationPrinter
     */
    public void setConstraintViolationPrinter(final ConstraintViolationPrinter constraintViolationPrinter) {
        Assert.notNull(constraintViolationPrinter, "Printer should not be null");
        this.printer = constraintViolationPrinter;
    }

    /**
     * Validates a Bean for the specified goups. Prints formatted error-messages on validation-errors when 'printErrors'
     * is set to true.
     *
     * @param  entity
     * @param  printErrors
     * @param  groups
     */
    public <T> void validate(final T entity, final boolean printErrors, final Class<?>... groups) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity, groups);
        if (violations.isEmpty()) {
            return;
        }

        Set<ConstraintViolation<?>> genericViolations = new HashSet<ConstraintViolation<?>>(violations);
        if (printErrors) {
            printer.printValidationErrors(genericViolations);
        }

        throw new ConstraintViolationException(genericViolations);
    }

}
