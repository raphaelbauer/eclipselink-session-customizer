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

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.google.common.base.Preconditions;

/**
 * Builds an simple message and prints it to specific target.
 *
 * @author  jbellmann
 * @see     {@link LogConstraintViolationPrinter}
 * @see     {@link SystemErrConstraintViolationPrinter}
 */
public abstract class AbstractConstraintViolationPrinter implements ConstraintViolationPrinter {

    static final String NOT_NULL_MSG = "ConstraintViolationMessageBuilder should not be null";

    private ConstraintViolationMessageBuilder constraintViolationMessageBuilder =
        new DefaultConstraintViolationMessageBuilder();

    @Override
    public void printValidationErrors(final Set<ConstraintViolation<?>> violations) {
        String message = buildMessage(violations);
        printMessage(message);
    }

    public abstract void printMessage(String message);

    protected String buildMessage(final Set<ConstraintViolation<?>> violations) {
        return constraintViolationMessageBuilder.buildMessage(violations);
    }

    @Override
    public void printValidationErrors(final ConstraintViolationException constraintViolationException) {
        printValidationErrors(constraintViolationException.getConstraintViolations());
    }

    public void setConstraintViolationMessageBuilder(
            final ConstraintViolationMessageBuilder constraintViolationMessageBuilder) {

        Preconditions.checkNotNull(constraintViolationMessageBuilder, NOT_NULL_MSG);

        this.constraintViolationMessageBuilder = constraintViolationMessageBuilder;
    }

}
