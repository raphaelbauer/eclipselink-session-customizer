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
/**
 * Marcel Wieczorek
 * Zalando GmbH
 * Nov 9, 2012 3:16:06 PM
 */
package org.zalando.jpa.eclipselink;

import java.sql.SQLException;

import org.eclipse.persistence.internal.localization.LoggingLocalization;
import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author  <a href="mailto:marcel.wieczorek@zalando.de" title="Marcel Wieczorek">mwieczorek</a>
 */
public class Slf4jSessionLog extends AbstractSessionLog {

    private static final Logger LOG = LoggerFactory.getLogger("org.eclipse.persistence");

    public Slf4jSessionLog() {
        setShouldPrintThread(true);
    }

    /* (non-Javadoc)
     * @see org.eclipse.persistence.logging.AbstractSessionLog#log(org.eclipse.persistence.logging.SessionLogEntry)
     */
    @Override
    public void log(final SessionLogEntry sessionLogEntry) {
        if (shouldLog(sessionLogEntry.getLevel(), sessionLogEntry.getNameSpace())) {
            synchronized (this) {
                final String category = sessionLogEntry.getNameSpace();

                final StringBuilder sb = new StringBuilder();

                switch (level) {

                    case SEVERE :
                        if (SEVERE_PREFIX == null) {
                            SEVERE_PREFIX = LoggingLocalization.buildMessage("toplink_severe");
                        }

                        sb.append(SEVERE_PREFIX);
                        break;

                    case WARNING :
                        if (WARNING_PREFIX == null) {
                            WARNING_PREFIX = LoggingLocalization.buildMessage("toplink_warning");
                        }

                        sb.append(WARNING_PREFIX);
                        break;

                    case INFO :
                        if (INFO_PREFIX == null) {
                            INFO_PREFIX = LoggingLocalization.buildMessage("toplink_info");
                        }

                        sb.append(INFO_PREFIX);
                        break;

                    case CONFIG :
                        if (CONFIG_PREFIX == null) {
                            CONFIG_PREFIX = LoggingLocalization.buildMessage("toplink_config");
                        }

                        sb.append(CONFIG_PREFIX);
                        break;

                    case FINE :
                        if (FINE_PREFIX == null) {
                            FINE_PREFIX = LoggingLocalization.buildMessage("toplink_fine");
                        }

                        sb.append(FINE_PREFIX);
                        break;

                    case FINER :
                        if (FINER_PREFIX == null) {
                            FINER_PREFIX = LoggingLocalization.buildMessage("toplink_finer");
                        }

                        sb.append(FINER_PREFIX);
                        break;

                    case FINEST :
                        if (FINEST_PREFIX == null) {
                            FINEST_PREFIX = LoggingLocalization.buildMessage("toplink_finest");
                        }

                        sb.append(FINEST_PREFIX);
                        break;

                    default :
                        if (TOPLINK_PREFIX == null) {
                            TOPLINK_PREFIX = LoggingLocalization.buildMessage("toplink");
                        }

                        sb.append(TOPLINK_PREFIX);
                }

                if (category != null) {
                    sb.append("[").append(category).append("]: ");
                }

                sb.append(getSupplementDetailString(sessionLogEntry));

                if (sessionLogEntry.hasMessage()) {
                    sb.append(formatMessage(sessionLogEntry));
                }

                if (sessionLogEntry.hasException()) {
                    final Throwable t = sessionLogEntry.getException();
                    sb.append(t.toString());

                    if (shouldLogExceptionStackTrace()) {
                        LOG.error(sb.toString(), t);
                        doExhaustiveLoggingIfExceptionCausedBySQLException(t);
                    } else {
                        LOG.error(sb.toString());
                    }
                } else {
                    switch (level) {

                        case SEVERE :
                            LOG.error(sb.toString());
                            break;

                        case WARNING :
                            LOG.warn(sb.toString());
                            break;

                        case INFO :
                            LOG.info(sb.toString());
                            break;

                        case FINE :
                            LOG.debug(sb.toString());
                            break;

                        case FINER :
                            LOG.debug(sb.toString());
                            break;

                        case FINEST :
                            LOG.debug(sb.toString());
                            break;

                        case ALL :
                            LOG.debug(sb.toString());
                            break;

                        default :
                            LOG.info(sb.toString());
                    }
                }
            }
        }
    }

    private void doExhaustiveLoggingIfExceptionCausedBySQLException(final Throwable throwable) {

        // This is needed to really know what caused an SQLException. It will
        // tell us e.g.  about violated constraints in the database. This
        // makes debugging a lot simpler when database errors occur.
        if (throwable.getCause() instanceof SQLException) {
            SQLException sqlException = (SQLException) throwable.getCause();
            for (Throwable nextException : sqlException) {
                LOG.error("[SQLException nextException]", nextException);
            }
        }
    }

}
