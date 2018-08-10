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
package fr.aesn.rade.rs;

/**
 * Exception for REST WebService Request.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@SuppressWarnings("serial")
public class RestRequestException
  extends Exception {
  /**
   * Constructs a new exception with null as its detail message.
   */
  public RestRequestException() {
    super();
  }

  /**
   * Constructs a new exception with the specified cause and a detail message.
   * @param message the detail message.
   * @param cause the cause.
   */
  public RestRequestException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified detail message.
   * @param message the detail message.
   */
  public RestRequestException(final String message) {
    super(message);
  }

  /**
   * Constructs a new exception with the specified cause and a detail message.
   * @param cause the cause.
   */
  public RestRequestException(final Throwable cause) {
    super(cause);
  }
}
