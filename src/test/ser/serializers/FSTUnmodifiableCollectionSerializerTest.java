/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ser.serializers;

import org.junit.Test;
import org.nustaq.serialization.FSTConfiguration;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Jakub Kubrynski
 */
@SuppressWarnings("unchecked")
public class FSTUnmodifiableCollectionSerializerTest {

    private static final Class<?> UNMODIFIABLE_COLLECTION_CLASS;
    private static final Class<?> UNMODIFIABLE_LIST_CLASS;
    private static final Class<?> UNMODIFIABLE_SET_CLASS;

    static {
        UNMODIFIABLE_COLLECTION_CLASS = Collections.unmodifiableCollection(new ArrayList()).getClass();
        UNMODIFIABLE_LIST_CLASS = Collections.unmodifiableList(new ArrayList()).getClass();
        UNMODIFIABLE_SET_CLASS = Collections.unmodifiableSet(new HashSet()).getClass();
    }

    private static final String TEST_VALUE = "TestValue";

    @Test
    public void shouldSerializeUnmodifiableCollection() throws ClassNotFoundException {
        //given
        Collection<String> list = Collections.unmodifiableCollection(Collections.singletonList(TEST_VALUE));
        FSTConfiguration conf = FSTConfiguration.createJsonNoRefConfiguration();
        //when
        list = (Collection<String>) conf.asObject(conf.asByteArray((list)));
        //then
        assertTrue(UNMODIFIABLE_COLLECTION_CLASS.isAssignableFrom(list.getClass()));
        assertEquals(1, list.size());
        assertTrue(list.contains(TEST_VALUE));
    }

    @Test
    public void shouldSerializeUnmodifiableList() throws ClassNotFoundException {
        //given
        List<String> list = Collections.unmodifiableList(Collections.singletonList(TEST_VALUE));
        FSTConfiguration conf = FSTConfiguration.createJsonNoRefConfiguration();

        // FIXME: java.lang.RuntimeException: cannot support legacy JDK serialization methods in crossplatform mode.
        // FIXME: Define a serializer for this class java.util.Collections$UnmodifiableRandomAccessList

        //when
        list = (List<String>) conf.asObject(conf.asByteArray((list)));
        //then
        assertTrue(UNMODIFIABLE_LIST_CLASS.isAssignableFrom(list.getClass()));
        assertEquals(1, list.size());
        assertTrue(list.contains(TEST_VALUE));
    }

    @Test
    public void shouldSerializeUnmodifiableSet() throws ClassNotFoundException {
        //given
        Set<String> set = Collections.unmodifiableSet(Collections.singleton(TEST_VALUE));
        FSTConfiguration conf = FSTConfiguration.createJsonNoRefConfiguration();
        //when
        byte[] bytes = conf.asByteArray((set));
        set = (Set<String>) conf.asObject(bytes);
        //then
        assertTrue(UNMODIFIABLE_SET_CLASS.isAssignableFrom(set.getClass()));
        assertEquals(1, set.size());
        assertTrue(set.contains(TEST_VALUE));
    }

}