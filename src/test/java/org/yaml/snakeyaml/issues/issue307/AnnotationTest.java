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
package org.yaml.snakeyaml.issues.issue307;

import junit.framework.TestCase;
import org.yaml.snakeyaml.Util;
import org.yaml.snakeyaml.Yaml;

public class AnnotationTest extends TestCase {

    private String s = Util.getLocalResource("issues/issue307.yaml");;
    private Yaml yaml = new Yaml();

    public void test_sorted_in_order() {
        YAMLFieldBean1 bean1 = yaml.loadAs(s, YAMLFieldBean1.class);
        String dump = yaml.dump(bean1);
        String s2 = "!!org.yaml.snakeyaml.issues.issue307.YAMLFieldBean1\n" +
                "name: tian\n" +
                "type: {z: 256, y: 255, x: 254}\n" +
                "age: 22\n" +
                "line: twelve\n" +
                "text: omit\n";
        assertEquals(dump,s2);
    }

    public void test_negative_order_prior_to_positive_order() {

        YAMLFieldBean2 bean2 = yaml.loadAs(s, YAMLFieldBean2.class);
        String dump = yaml.dump(bean2);
        String s2 = "!!org.yaml.snakeyaml.issues.issue307.YAMLFieldBean2\n" +
                "text: omit\n" +
                "type: {z: 256, y: 255, x: 254}\n" +
                "name: tian\n" +
                "age: 22\n" +
                "line: twelve\n";
        assertEquals(dump,s2);
    }

    public void test_same_order_is_sorted_alpha() {
        YAMLFieldBean3 bean3 = yaml.loadAs(s, YAMLFieldBean3.class);
        String dump = yaml.dump(bean3);
        String s2 = "!!org.yaml.snakeyaml.issues.issue307.YAMLFieldBean3\n" +
                "line: twelve\n" +
                "text: omit\n" +
                "age: 22\n" +
                "name: tian\n" +
                "type: {z: 256, y: 255, x: 254}\n";
        assertEquals(dump,s2);
    }

    public void test_default_order_is_0() {
        YAMLFieldBean4 bean4 = yaml.loadAs(s, YAMLFieldBean4.class);
        String dump = yaml.dump(bean4);
        String s2 = "!!org.yaml.snakeyaml.issues.issue307.YAMLFieldBean4\n" +
                "name: tian\n" +
                "age: 22\n" +
                "line: twelve\n" +
                "type: {z: 256, y: 255, x: 254}\n" +
                "text: omit\n";
        assertEquals(dump,s2);
    }

    public void test_extend_order() {
        YAMLFieldBean5 bean5 = yaml.loadAs(s, YAMLFieldBean5.class);
        bean5.setPojoName("pojo");
        String dump = yaml.dump(bean5);
        String s2 = "!!org.yaml.snakeyaml.issues.issue307.YAMLFieldBean5\n" +
                "pojoName: pojo\n" +
                "name: tian\n" +
                "type: {z: 256, y: 255, x: 254}\n" +
                "age: 22\n" +
                "line: twelve\n" +
                "text: omit\n";
        assertEquals(dump,s2);
    }

}
