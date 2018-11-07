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
package fr.aesn.rade.persist.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * Collection of Tools for Manipulation Annotations during Runtime.
 * In particular it is useful for changing Entity Annotations such as
 * "@GeneratedValue" to change the Strategy.
 * NB: This class is Java 8 compatible
 * The methods in this class should be static and stateless.
 * @author Marc Gimpel (mgimpel@gmail.com)
 */
@Slf4j
public final class AnnotationUtils {
  /**
   * Hidden private Constructor makes the class non-instantiable.
   */
  private AnnotationUtils() {
    // Empty Constructor.
  }

  @SuppressWarnings("unchecked")
  public static Map<Class<? extends Annotation>, Annotation> getClassAnnotations(Class<?> clazz)
    throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
    Method method = Class.class.getDeclaredMethod("annotationData");
    method.setAccessible(true);
    Object annotationData = method.invoke(clazz);
    Field classAnnotations = annotationData.getClass().getDeclaredField("annotations");
    classAnnotations.setAccessible(true);
    Map<Class<? extends Annotation>, Annotation> map = null;
    try {
      map = (Map<Class<? extends Annotation>, Annotation>) classAnnotations.get(annotationData);
    } catch (IllegalAccessException e) {
      log.warn("this should never happen (just changed accessibility)", e);
    }
    classAnnotations.setAccessible(false);
    return map;
  }

  @SuppressWarnings("unchecked")
  public static Map<Class<? extends Annotation>, Annotation> getFieldAnnotations(Class<?> clazz, String fieldName)
    throws NoSuchFieldException {
    Field field = clazz.getDeclaredField(fieldName);
    field.getAnnotation(Annotation.class); // ensure declaredAnnotations is initialized
    Field fieldAnnotations = Field.class.getDeclaredField("declaredAnnotations");
    fieldAnnotations.setAccessible(true);
    Map<Class<? extends Annotation>, Annotation> map = null;
    try {
      map = (Map<Class<? extends Annotation>, Annotation>) fieldAnnotations.get(field);
    } catch (IllegalAccessException e) {
      log.warn("this should never happen (just changed accessibility)", e);
    }
    fieldAnnotations.setAccessible(false);
    return map;
  }
}
