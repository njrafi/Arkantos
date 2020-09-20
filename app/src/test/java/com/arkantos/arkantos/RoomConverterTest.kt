package com.arkantos.arkantos

import com.arkantos.arkantos.database.RoomConverters
import org.junit.Assert
import org.junit.Test

class RoomConverterTest {

    @Test
    fun testConvert() {
        val converter = RoomConverters()
        val listOfString = listOf<String>("dummyString1","dummyString2")
        val jsonString = converter.fromStringListToString(listOfString)
        val convertedListOfString = converter.fromStringToListString(jsonString)
        Assert.assertEquals(listOfString,convertedListOfString)
    }
}