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
package org.zalando.jpa.eclipselink.customizer.databasemapping.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author  jbellmann
 */
public abstract class EntityFieldInspector<T extends Annotation> {

    protected Field field;
    protected T annotation = null;
    protected Class<T> annotationClass;
    protected String nameValue = null;

    protected EntityFieldInspector(final Class<T> annotationClass, final Field field) {
        this.annotationClass = annotationClass;
        this.field = field;
        inspect();
    }

    protected void inspect() {
        if (field != null) {
            annotation = field.getAnnotation(annotationClass);
            if (annotation != null) {
                nameValue = resolveNameValue(annotation);
            }
        }
    }

    protected abstract String resolveNameValue(T annotation);

    public boolean isNameValueSet() {
        return !"".equals(getNameValue());
    }

    public String getNameValue() {
        return nameValue != null ? nameValue : "";
    }

    public Class<?> getFieldType() {
        return field != null ? field.getType() : null;
    }

    public Field getField() {
        return field;
    }
}
