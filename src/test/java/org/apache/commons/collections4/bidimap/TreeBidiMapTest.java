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

import static org.apache.commons.collections4.bidimap.TreeBidiMap.coverageCounter;

import java.util.TreeMap;

import org.apache.commons.collections4.BidiMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


/**
 * JUnit tests.
 */
public class TreeBidiMapTest<K extends Comparable<K>, V extends Comparable<V>> extends AbstractOrderedBidiMapTest<K, V> {

    @BeforeAll
    public static void beforeAllTests() {
        coverageCounter = new int[32];
    }

    @AfterAll
    public static void afterAllTests() {
        System.out.println("\n========== Branch Coverage: swapPosition() ===========");
        int covered = 0;

        for (int i = 0; i < coverageCounter.length; i++) {
            if (coverageCounter[i] > 0) {
                covered++;
            }
            final String name = "B" + String.format("%02d", i);
            final String status = (coverageCounter[i] == 0) ? "[MISSED]" : "[HIT-" + coverageCounter[i] + "x]";
            System.out.println(name + " : " + status);
        }

        final double percent = 100.0 * covered / coverageCounter.length;
        System.out.printf("\nCoverage: %d/%d branches (%.1f%%)\\n", covered, coverageCounter.length, percent);
        System.out.println("\n==============================================\n");
    }

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

}
