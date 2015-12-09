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
package org.zalando.jpa.eclipselink.customizer.classdescriptor;

import org.eclipse.persistence.annotations.ChangeTrackingType;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.changetracking.AttributeChangeTrackingPolicy;
import org.eclipse.persistence.descriptors.changetracking.DeferredChangeDetectionPolicy;
import org.eclipse.persistence.descriptors.changetracking.ObjectChangeTrackingPolicy;
import org.eclipse.persistence.sessions.Session;
import org.zalando.jpa.eclipselink.LogSupport;
import org.zalando.jpa.eclipselink.PersistenceUnitProperties;

/**
 * This {@link ClassDescriptorCustomizer} customizes {@link ChangeTrackingType} for an {@link ClassDescriptor}.
 *
 * @author  jbellmann
 * @author  ahartmann
 */
public class ChangePolicyClassDescriptorCustomizer extends LogSupport implements ClassDescriptorCustomizer {

    @Override
    public void customize(final ClassDescriptor clazzDescriptor, final Session session) {
        customizeObjectChangePolicy(clazzDescriptor, session);
    }

    private void customizeObjectChangePolicy(final ClassDescriptor clazzDescriptor, final Session session) {
        final String propertyValue = (String) session.getProperty(
                PersistenceUnitProperties.ZALANDO_ECLIPSELINK_CHANGE_TRACKER_NAME);

        ChangeTrackingType changeTrackingType = ChangeTrackingType.AUTO;

        if (propertyValue != null && (!propertyValue.trim().isEmpty())) {
            try {
                changeTrackingType = ChangeTrackingType.valueOf(propertyValue);
            } catch (Exception e) {
                logWarning(session, COULD_NOT_DETERMINE_CHANGE_TRACKING_TYPE, propertyValue);
                changeTrackingType = ChangeTrackingType.AUTO;
            }
        }

        switch (changeTrackingType) {

            case DEFERRED :
                clazzDescriptor.setObjectChangePolicy(new DeferredChangeDetectionPolicy());
                logFine(session, SET_OBJECT_CHANGE_POLICY_TO, DEFERRED_CHANGE_DETECTION_POLICY);
                break;

            case OBJECT :
                clazzDescriptor.setObjectChangePolicy(new ObjectChangeTrackingPolicy());
                logFine(session, SET_OBJECT_CHANGE_POLICY_TO, OBJECT_CHANGE_TRACKING_POLICY);
                break;

            case ATTRIBUTE :
                clazzDescriptor.setObjectChangePolicy(new AttributeChangeTrackingPolicy());
                logFine(session, SET_OBJECT_CHANGE_POLICY_TO, ATTRIBUTE_CHANGE_TRACKING_POLICY);
                break;

            case AUTO :
            default :
                logFine(session, USE_DEFAULT_CHANGE_TRACKING_POLICY);
        }
    }

}
