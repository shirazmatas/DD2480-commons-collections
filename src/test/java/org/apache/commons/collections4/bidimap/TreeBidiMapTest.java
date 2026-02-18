/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.bidimap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.OrderedIterator;
import org.apache.commons.collections4.OrderedMapIterator;
import org.junit.jupiter.api.Test;



/**
 * JUnit tests.
 */
public class TreeBidiMapTest<K extends Comparable<K>, V extends Comparable<V>> extends AbstractOrderedBidiMapTest<K, V> {

    @Override
    public String getCompatibilityVersion() {
        return "4";
    }

    @Override
    public boolean isAllowNullKey() {
        return false;
    }

    @Override
    public boolean isAllowNullValueGet() {
        return false;
    }

    @Override
    public boolean isAllowNullValuePut() {
        return false;
    }

    @Override
    public boolean isSetValueSupported() {
        return false;
    }

    @Override
    public TreeMap<K, V> makeConfirmedMap() {
        return new TreeMap<>();
    }

    @Override
    public BidiMap<K, V> makeObject() {
        return new TreeBidiMap<>();
    }

//    void testCreate() throws Exception {
//        resetEmpty();
//        writeExternalFormToDisk((java.io.Serializable) map, "src/test/resources/data/test/TreeBidiMap.emptyCollection.version4.obj");
//        resetFull();
//        writeExternalFormToDisk((java.io.Serializable) map, "src/test/resources/data/test/TreeBidiMap.fullCollection.version4.obj");
//    }

    /**
     * Test that ViewMapEntryIterator correctly iterates forward and backward over TreeBidiMap entries.
     */
    @Test
    void testViewMapEntryIterator() {
        // build map and iterator
        final TreeBidiMap<Integer, String> map = new TreeBidiMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        final OrderedIterator<Map.Entry<Integer, String>> it = (OrderedIterator<Map.Entry<Integer, String>>) map.entrySet().iterator();

        // iterating forward over all entries:
            // there is a next entry
            // the entry retrieved cannot be null
            // the key and value match the expected entry

        assertTrue(it.hasNext());
        Map.Entry<Integer, String> entry = it.next();
        assertNotNull(entry);
        assertEquals(1, entry.getKey());
        assertEquals("one", entry.getValue());

        assertTrue(it.hasNext());
        entry = it.next();
        assertNotNull(entry);
        assertEquals(2, entry.getKey());
        assertEquals("two", entry.getValue());

        assertTrue(it.hasNext());
        entry = it.next();
        assertNotNull(entry);
        assertEquals(3, entry.getKey());
        assertEquals("three", entry.getValue());

        // reached end of iteration
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);

        // iterating backward over all entries:
            // there is a previous entry
            // the entry retrieved cannot be null
            // the key and value match the expected entry

        assertTrue(it.hasPrevious());
        entry = it.previous();
        assertNotNull(entry);
        assertEquals(3, entry.getKey());
        assertEquals("three", entry.getValue());

        assertTrue(it.hasPrevious());
        entry = it.previous();
        assertNotNull(entry);
        assertEquals(2, entry.getKey());
        assertEquals("two", entry.getValue());

        assertTrue(it.hasPrevious());
        entry = it.previous();
        assertNotNull(entry);
        assertEquals(1, entry.getKey());
        assertEquals("one", entry.getValue());

        // reached start of iteration
        assertFalse(it.hasPrevious());
        assertThrows(NoSuchElementException.class, it::previous);
    }

    /**
     * Test that ViewMapEntryIterator correctly iterates forward and backward over TreeBidiMap.
     */
    @Test
    void testViewMapIterator() {
        // build map and iterator
        final TreeBidiMap<Integer, String> map = new TreeBidiMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        final OrderedMapIterator<Integer, String> it = map.mapIterator();

        // cannot get key or value before at least one iteration
        assertThrows(IllegalStateException.class, it::getKey);
        assertThrows(IllegalStateException.class, it::getValue);

        // iterating forward over all entries:
            // there is a next entry
            // the key and value match the expected entry

        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        assertEquals(1, it.getKey());
        assertEquals("one", it.getValue());

        assertTrue(it.hasNext());
        assertEquals(2, it.next());
        assertEquals(2, it.getKey());
        assertEquals("two", it.getValue());

        assertTrue(it.hasNext());
        assertEquals(3, it.next());
        assertEquals(3, it.getKey());
        assertEquals("three", it.getValue());

        // reached end of iteration
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);

        // iterating backward over all entries:
            // there is a previous entry
            // the key and value match the expected entry

        assertTrue(it.hasPrevious());
        assertEquals(3, it.previous());
        assertEquals(3, it.getKey());
        assertEquals("three", it.getValue());

        assertTrue(it.hasPrevious());
        assertEquals(2, it.previous());
        assertEquals(2, it.getKey());
        assertEquals("two", it.getValue());

        assertTrue(it.hasPrevious());
        assertEquals(1, it.previous());
        assertEquals(1, it.getKey());
        assertEquals("one", it.getValue());

        // reached start of iteration
        assertFalse(it.hasPrevious());
        assertThrows(NoSuchElementException.class, it::previous);
    }

    /**
     * Test that equals() method of TreeBidiMap nodes correctly identifies nodes with
     * same key and value.
     */
    @Test
    void testNodeEquals() {
        // initialize nodes
        final TreeBidiMap.Node<Integer, String> node = new TreeBidiMap.Node<>(1, "one");
        final TreeBidiMap.Node<Integer, String> sameKeyValue = new TreeBidiMap.Node<>(1, "one");
        final TreeBidiMap.Node<Integer, String> diffKey = new TreeBidiMap.Node<>(2, "one");
        final TreeBidiMap.Node<Integer, String> diffValue = new TreeBidiMap.Node<>(1, "two");
        final Object diffType = new Object();

        // node equal to itself
        assertEquals(node, node);
        // node equal to other node with same fields
        assertEquals(node, sameKeyValue);
        // node not equal to node with different key
        assertNotEquals(node, diffKey);
        // node not equal to node with different value
        assertNotEquals(node, diffValue);
        // node not equal to object of different type
        assertNotEquals(node, diffType);
    }

    /**
     * Test that TreeBidiMap copy constructor correctly returns a copied map.
     */
    @Test
    void testCopyConstructor() {
        // construct map
        final TreeBidiMap<Integer, String> map = new TreeBidiMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");

        // map equals copied map
        final TreeBidiMap<Integer, String> mapCopy = new TreeBidiMap<>(map);
        assertEquals(map, mapCopy);
    }

}
