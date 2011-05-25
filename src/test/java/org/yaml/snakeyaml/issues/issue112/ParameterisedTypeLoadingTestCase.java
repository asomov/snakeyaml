/**
 * Copyright (c) 2008-2011, http://www.snakeyaml.org
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

package org.yaml.snakeyaml.issues.issue112;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.yaml.snakeyaml.JavaBeanDumper;
import org.yaml.snakeyaml.Util;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class ParameterisedTypeLoadingTestCase {

    @Test
    public void testParameterisedTypeLoading() throws IOException {
        Yaml yamlParser = new Yaml(new Constructor(MyCompositeObject.class));

        MyCompositeObject obj = (MyCompositeObject) yamlParser.load(this.getClass()
                .getClassLoader().getResource("issues/issue112-1.yaml").openStream());

        assertNotNull(obj);

        for (Object thing : obj.getThings()) {
            assertEquals(MyClass.class, thing.getClass());
            @SuppressWarnings("unchecked")
            MyClass<Object> mclass = (MyClass<Object>) thing;
            assertNotNull("The 'name' property must be set.", mclass.getName());
            assertTrue("'name' must not be empty.", mclass.getName().toString().length() > 0);
        }

        // dump the object
        JavaBeanDumper dumper = new JavaBeanDumper();
        String output = dumper.dump(obj);
        assertEquals(Util.getLocalResource("issues/issue112-2.yaml"), output);
    }
}