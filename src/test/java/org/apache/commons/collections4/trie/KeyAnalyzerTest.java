package org.apache.commons.collections4.trie;

import org.apache.commons.collections4.trie.analyzer.StringKeyAnalyzer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KeyAnalyzerTest {

    /**
     * Tests the compare method of the KeyAnalyzer class.
     * The compare method compares two keys and returns:
     * - 0 if both keys are equal or null,
     * - -1 if the first key is null or less than the second,
     * - 1 if the first key is more than the second or the second is null.
     */
    @Test
    void testIsEqualBitKey() {
       // Should be true only for -2
        assertTrue(KeyAnalyzer.isEqualBitKey(-2));
        assertFalse(KeyAnalyzer.isEqualBitKey(-1));
        assertFalse(KeyAnalyzer.isEqualBitKey(0));
    }

    @Test
    void testIsNullBitKey() {
        // Should be true only for NULL_BIT_KEY (-1)
        assertTrue(KeyAnalyzer.isNullBitKey(-1));
        assertFalse(KeyAnalyzer.isNullBitKey(-2));
        assertFalse(KeyAnalyzer.isNullBitKey(5));
    }

    @Test
    void testIsOutOfBoundsIndex() {
        // Should be true only for OUT_OF_BOUNDS_BIT_KEY (-3)
        assertTrue(KeyAnalyzer.isOutOfBoundsIndex(-3));
        assertFalse(KeyAnalyzer.isOutOfBoundsIndex(-1));
        assertFalse(KeyAnalyzer.isOutOfBoundsIndex(10));
    }

    @Test
    void testIsValidBitIndex() {
        // Valid indices are >= 0
        assertTrue(KeyAnalyzer.isValidBitIndex(0), "0 should be a valid bit index");
        assertTrue(KeyAnalyzer.isValidBitIndex(100), "Positive integers should be valid bit indices");
        assertFalse(KeyAnalyzer.isValidBitIndex(-1), "NULL_BIT_KEY should not be a valid index");
        assertFalse(KeyAnalyzer.isValidBitIndex(-2), "EQUAL_BIT_KEY should not be a valid index");
        assertFalse(KeyAnalyzer.isValidBitIndex(-3), "OUT_OF_BOUNDS_BIT_KEY should not be a valid index");
    }
}