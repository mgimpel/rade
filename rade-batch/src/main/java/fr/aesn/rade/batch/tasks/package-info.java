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
/**
 * This package contains the tasklet and chunk orientated components for the
 * Rade Batch Scripts.
 *
 * In chunk oriented jobs, within each step, Spring Batch will Read, Process
 * and then Write items/lines in chunks. As such this package contains:
 * <ul>
 * <li>ItemReaders and their helper classes (such as line/fieldset mappers
 * that map a line from a CSV or fixed length data file to a java object or
 * model)</li>
 * <li>ItemProcessors (that transform and enrich an item passed to it)</li>
 * <li>ItemWriters (that write/persist an item passed to it)</li>
 * </ul>
 *
 * In tasklet oriented jobs, within each step, the tasklet will perform the
 * complete task at hand. So this package also contains:
 * <ul>
 * <li>Tasklets (that perform a complete task in one step)</li>
 * </ul>
 *
 * The sub-packages group the components into separate fields that process data
 * related to different topics or from different sources such as: INSEE,
 * Sandre, Hexaposte, ...
 */
package fr.aesn.rade.batch.tasks;
