package com.zkv.tfsfeed

import com.zkv.tfsfeed.domain.utils.currencyCountWithSuffix
import junit.framework.Assert.assertEquals
import org.junit.Test

class CurrencyCountWithSuffixTests {

    @Test
    fun checkCurrencyCountWithSuffix() {
        assertEquals("0", 0.currencyCountWithSuffix)
        assertEquals("42", 42.currencyCountWithSuffix)
    }

    @Test
    fun checkCurrencyCountWithSuffixForK() {
        assertEquals("1,5K", 1500.currencyCountWithSuffix)
        assertEquals("1,6K", 1587.currencyCountWithSuffix)
    }

    @Test
    fun checkCurrencyCountWithSuffixForM() {
        assertEquals("1,0M", 999_999.currencyCountWithSuffix)
        assertEquals("1,6M", 1_599_000.currencyCountWithSuffix)
    }

    @Test
    fun checkCurrencyCountWithSuffixForB() {
        assertEquals("1,0B", 999_999_999.currencyCountWithSuffix)
        assertEquals("1,6B", 1_599_000_000.currencyCountWithSuffix)
    }
}
