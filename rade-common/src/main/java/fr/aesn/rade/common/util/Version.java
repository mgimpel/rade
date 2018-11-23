/*  This file is part of the Rade project (https://github.com/mgimpel/rade).
 *  Copyright (C) 2018 Marc Gimpel
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
/* $Id$ */
package fr.aesn.rade.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * Project Version Info.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
public final class Version {
  /** Singleton instance. */
  private static final Version instance = new Version();
  /** Properties containing the values loaded from the Version file. */
  private static final Properties versionProperties;
  /** Name of the file with the version details. */
  protected static final String PROPERTIES_FILE = "/version.properties";
  /** Name of the property for the project version. */
  protected static final String PROJECT_VERSION_PROPERTY = "version";
  /** Name of the property for the repository revision. */
  protected static final String REPOSITORY_REVISION_PROPERTY = "revision";
  /** Name of the property for the build timestamp. */
  protected static final String BUILD_TIMESTAMP_PROPERTY = "timestamp";
  /** The project version. */
  public static final String PROJECT_VERSION;
  /** The repository revision. */
  public static final String REPOSITORY_REVISION;
  /** The build timestamp. */
  public static final String BUILD_TIMESTAMP;

  /** Static Initializer. */
  static {
    versionProperties = new Properties();
    InputStream is = Version.class.getResourceAsStream(PROPERTIES_FILE);
    try {
      versionProperties.load(is);
    } catch (IOException ex) {
      log.warn("Could not load Version information.", ex);
    } finally {
      try {
        is.close();
      } catch (IOException ex) {
        log.error("This should never happen.", ex);
      }
    }
    PROJECT_VERSION = versionProperties
        .getProperty(PROJECT_VERSION_PROPERTY);
    REPOSITORY_REVISION = versionProperties
        .getProperty(REPOSITORY_REVISION_PROPERTY);
    BUILD_TIMESTAMP = versionProperties
        .getProperty(BUILD_TIMESTAMP_PROPERTY);
    log.debug("Project Version {}, "
              + "Repository revision {}, "
              + "Build timestamp {}",
              PROJECT_VERSION,
              REPOSITORY_REVISION,
              BUILD_TIMESTAMP);
  }

  /**
   * Private constructor to hide the implicit public one.
   * Utility classes, which are a collection of static members,
   * are not meant to be instantiated.
   */
  private Version() {
    // Private constructor to hide the implicit public one.
  }

  /**
   * Returns Singleton instance of Version.
   * @return Singleton instance of Version.
   */
  public static final Version getInstance() {
    return instance;
  }

  /**
   * Returns the Value for the given Key in the Version file.
   * @param key Property Key.
   * @return the Value for the given Key in the Version file.
   */
  public static final String getProperty(final String key) {
    return versionProperties.getProperty(key);
  }
}
