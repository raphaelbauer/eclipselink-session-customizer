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

import org.springframework.dao.DataIntegrityViolationException;

public class ReportingConstraintViolationException extends DataIntegrityViolationException {

    private static final long serialVersionUID = -7746311056397921637L;

    private static final ConstraintViolationMessageBuilder MESSAGE_BUILDER =
        new DefaultConstraintViolationMessageBuilder();

    public ReportingConstraintViolationException(final ConstraintViolationException cause) {
        super(cause.getMessage(), cause);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.core.NestedRuntimeException#getMessage()
     */
    @Override
    public String getMessage() {
        return MESSAGE_BUILDER.buildMessage(((ConstraintViolationException) getCause()).getConstraintViolations());
            // final StringBuilder sb = new StringBuilder();
            // final ConstraintViolationException e = (ConstraintViolationException)
            // getCause();
            // Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
            // for (final ConstraintViolation<?> violation : violations) {
            // sb.append("ConstraintViolationMessage: ").append(violation.getMessage());
            // sb.append("! Property \"").append(violation.getPropertyPath().toString()).append("\" of ");
            // if (!violation.getRootBean().equals(violation.getLeafBean())) {
            // sb.append(violation.getRootBean()).append(" ");
            // }
            //
            // sb.append(violation.getLeafBean());
            // sb.append(" has value: ");
            // sb.append(violation.getInvalidValue()).append("! ");
            // }
            //
            // sb.append("This might cause the error: ").append(e.getLocalizedMessage());
            //
            // return sb.toString();
    }
}
