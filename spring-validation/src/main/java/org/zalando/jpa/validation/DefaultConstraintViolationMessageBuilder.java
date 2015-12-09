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

/**
 * @author  jbellmann
 */
public class DefaultConstraintViolationMessageBuilder implements ConstraintViolationMessageBuilder {

    @Override
    public String buildMessage(final Set<ConstraintViolation<?>> violations) {
        StringBuilder sb = new StringBuilder("\n\n");
        for (final ConstraintViolation<?> violation : violations) {
            sb.append("\t[VALIDATION-ERROR]  ");
            if (!violation.getRootBean().equals(violation.getLeafBean())) {
                sb.append(violation.getRootBean()).append(" ");
            }

            sb.append(violation.getLeafBean().getClass().getSimpleName());
            sb.append("#");
            sb.append(violation.getPropertyPath().toString());
            sb.append(" -- '");
            sb.append(violation.getMessage());
            sb.append("', but has value : ");
            sb.append(violation.getInvalidValue());
            sb.append(" on object : ").append(violation.getLeafBean().toString());
            sb.append("\n");
        }

        return sb.toString();
    }

}
