/**
 * Copyright (c) 2008, http://www.snakeyaml.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yaml.snakeyaml.introspector;

import org.yaml.snakeyaml.YAMLField;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * <p>
 * A <code>Property</code> represents a single member variable of a class,
 * possibly including its accessor methods (getX, setX). The name stored in this
 * class is the actual name of the property as given for the class, not an
 * alias.
 * </p>
 *
 * <p>
 * Objects of this class have a total ordering which defaults to ordering based
 * on the name of the property.
 * </p>
 */
public abstract class Property implements Comparable<Property> {

    private final String name;
    private final Class<?> type;

    public Property(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

    abstract public Class<?>[] getActualTypeArguments();

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName() + " of " + getType();
    }

    public int compareTo(Property o) {
        return getName().compareTo(o.getName());
    }

    public boolean isWritable() {
        return true;
    }

    public boolean isReadable() {
        return true;
    }

    abstract public void set(Object object, Object value) throws Exception;

    abstract public Object get(Object object);

    /**
     * Returns the annotations that are present on this property or empty {@code List} if there're no annotations.
     *
     * @return the annotations that are present on this property or empty {@code List} if there're no annotations
     */
    abstract public List<Annotation> getAnnotations();

    /**
     * Returns property's annotation for the given type or {@code null} if it's not present.
     *
     * @param annotationType the type of the annotation to be returned
     * @param <A> class of the annotation
     *
     * @return property's annotation for the given type or {@code null} if it's not present
     */
    abstract public <A extends Annotation> A getAnnotation(Class<A> annotationType);

    public Field getField(final Class<? extends Object> type,String fieldName) {
        Field[] fields = type.getDeclaredFields();
        int var1 = fields.length;
        Field field;
        for (int i = 0; i < var1; ++i) {
            field = fields[i];
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        fields = type.getFields();
        var1 = fields.length;
        for (int i = 0; i < var1; ++i) {
            field = fields[i];
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    public Object getYAMLFieldAnnotation(Field field, String annotationName){
        try {
            YAMLField yamlField = null;
            if (field != null) {
                yamlField = field.getAnnotation(YAMLField.class);
            }
            if (yamlField != null) {
                Method method = yamlField.annotationType().getDeclaredMethod(annotationName);
                if (method != null) {
                    return method.invoke(yamlField, null);
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int hashCode() {
        return getName().hashCode() + getType().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Property) {
            Property p = (Property) other;
            return getName().equals(p.getName()) && getType().equals(p.getType());
        }
        return false;
    }
}