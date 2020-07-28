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
package org.yaml.snakeyaml.issues.issue361;

import junit.framework.TestCase;
import org.yaml.snakeyaml.TypeReference;
import org.yaml.snakeyaml.Util;
import org.yaml.snakeyaml.Yaml;

import java.util.*;

public class GenericTypeTest extends TestCase {

    public void test_generic_type_bean_property() {
        Yaml yaml = new Yaml();
        String str = Util.getLocalResource("issues/issue361-1.yml");
        Document doc1 = yaml.loadAs(str, Document.class);
        List<Map<String, Property>> properties = doc1.getProperties();
        assert(properties.get(0).get("property1") instanceof Property);

        Document doc2 = yaml.loadAs(str, new TypeReference<Document>() {});
        properties = doc2.getProperties();
        assert(properties.get(0).get("property1") instanceof Property);
    }

    public void test_ref_map() {
        Yaml yaml = new Yaml();
        String str = Util.getLocalResource("issues/issue361-2.yml");
        Map map = yaml.loadAs(str, new TypeReference<Map<String, Property>>(){});
        assert(map.get("property") instanceof Property);

        Map map2 = yaml.loadAs(str, new TypeReference<Map>(){});
        assert(map2.get("property") instanceof LinkedHashMap);

        Map map3 = yaml.loadAs(str, Map.class);
        assert(map3.get("property") instanceof LinkedHashMap);
    }

    public void test_ref_list() {
        Yaml yaml = new Yaml();
        String str = Util.getLocalResource("issues/issue361-3.yml");
        List list1 = yaml.loadAs(str, new TypeReference<List<Property>>(){});
        assert(list1.get(0) instanceof Property);

        List list2 = yaml.loadAs(str, new TypeReference<List>(){});
        assert(list2.get(0) instanceof LinkedHashMap);

        List list3 = yaml.loadAs(str, List.class);
        assert(list3.get(0) instanceof LinkedHashMap);
    }

    public void test_ref_list_of_map() {
        Yaml yaml = new Yaml();
        String str = Util.getLocalResource("issues/issue361-4.yml");
        List<Map<String, Property>> list = yaml.loadAs(str, new TypeReference<List<Map<String, Property>>>(){});
        assert(list.get(0).get("property1") instanceof Property);

        List list2 = yaml.loadAs(str, List.class);
        assert(((Map)list2.get(0)).get("property1") instanceof LinkedHashMap);
    }

    public void test_ref_list_of_list() {
        Yaml yaml = new Yaml();
        String str = Util.getLocalResource("issues/issue361-5.yml");
        List<List> list = yaml.loadAs(str, new TypeReference<List<List<Property>>>(){});
        assert(list.get(0).get(0) instanceof Property);

        List<List> list2 = yaml.loadAs(str, List.class);
        assert(list2.get(0).get(0) instanceof LinkedHashMap);
    }

    public void test_ref_map_of_list() {
        Yaml yaml = new Yaml();
        String str = Util.getLocalResource("issues/issue361-6.yml");
        Map<String, List<Property>> map = yaml.loadAs(str, new TypeReference<Map<String, List<Property>>>(){});
        assert(map.get("property").get(0) instanceof Property);
    }

    public void test_ref_map_of_map() {
        Yaml yaml = new Yaml();
        String str = Util.getLocalResource("issues/issue361-7.yml");
        Map<String, Map<String, Property>> map = yaml.loadAs(str, new TypeReference<Map<String, Map<String, Property>>>(){});
        assert(map.get("property").get("inner") instanceof Property);
    }
}
