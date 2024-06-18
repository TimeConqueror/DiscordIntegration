/*
 * Copyright (C) 2018 Chikachi and other contributors
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 */

package chikachi.discord.core;

import chikachi.discord.core.CoreConstants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DiscordIntegrationLogger {
    private static final Logger logger = LogManager.getLogger(CoreConstants.MODNAME);

    public static void Log(String message) {
        Log(message, false);
    }

    public static void Log(String message, boolean warning) {
        logger.log(warning ? Level.WARN : Level.INFO, String.format("[%s] %s", CoreConstants.VERSION, message));
    }

    public static void debug(String message) {
        logger.debug(String.format("[%s] %s", CoreConstants.VERSION, message));
    }

    public static void warning(String message) {
        logger.warn(String.format("[%s] %s", CoreConstants.VERSION, message));
    }

    public static void error(String message) {
        logger.error(String.format("[%s] %s", CoreConstants.VERSION, message));
    }

    public static void debug(Throwable exception) {
        logger.debug(exception);
    }

    public static void error(Throwable exception) {
        logger.error(exception);
    }
}
