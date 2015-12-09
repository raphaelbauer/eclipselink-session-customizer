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
package org.zalando.jpa.support;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;

import org.reflections.Reflections;

import org.reflections.scanners.TypeAnnotationsScanner;

import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.InitializingBean;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

import com.google.common.base.Preconditions;

/**
 * This PersistenceUnitPostProcessor is used to search given package list for JPA entities and add them as managed
 * entities. By default the JPA engine searches for persistent classes only in the same class-path of the location of
 * the persistence.xml file. When running unit tests the entities end up in test-classes folder which does not get
 * scanned. To avoid specifying each entity in the persistence.xml file to scan, this post processor automatically adds
 * the entities for you.
 */
public class MultiJarEntityPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor, InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(MultiJarEntityPersistenceUnitPostProcessor.class);

    /**
     * the calculated list of additional persistent classes.
     */
    private Set<Class<? extends Object>> persistentClasses;

    private String namespaceToScan;

    /**
     * Looks for any persistent class in the class-path under the specified packages.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Preconditions.checkNotNull(namespaceToScan, "namespaceToScan must not be null");
        LOG.debug("Looking for @Entity in jars with namespace [{}]", namespaceToScan);

        persistentClasses = new HashSet<Class<? extends Object>>();

        final Reflections reflections = new Reflections(new ConfigurationBuilder().filterInputsBy(
                    new FilterBuilder.Include(FilterBuilder.prefix(namespaceToScan))).setUrls(
                    ClasspathHelper.forPackage(namespaceToScan)).setScanners(new TypeAnnotationsScanner()));
        final Set<Class<?>> entityClasses = reflections.getTypesAnnotatedWith(Entity.class);
        LOG.debug("Fount entity classes:  " + entityClasses);
        persistentClasses.addAll(entityClasses);
        if (persistentClasses.isEmpty()) {
            throw new IllegalArgumentException("No class annotated with @Entity found in: " + namespaceToScan);
        }
    }

    /**
     * Add all the persistent classes found to the PersistentUnit.
     */
    @Override
    public void postProcessPersistenceUnitInfo(final MutablePersistenceUnitInfo persistenceUnitInfo) {
        for (Class<? extends Object> c : persistentClasses) {
            persistenceUnitInfo.addManagedClassName(c.getName());
        }
    }

    public void setNamespaceToScan(final String namespaceToScan) {
        this.namespaceToScan = namespaceToScan;
    }
}
