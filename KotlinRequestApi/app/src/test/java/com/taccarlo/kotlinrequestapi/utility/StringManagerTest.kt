package com.taccarlo.kotlinrequestapi.utility
import org.junit.Test

internal class StringManagerTest {
    @Test
    fun testAPIRequest() {
       val a = StringManager.dateConversion("Thu, 22 Apr 2021 00:00:00 +0000")
       org.junit.Assert.assertTrue( a=="22/Apr/2021" )
    }
}