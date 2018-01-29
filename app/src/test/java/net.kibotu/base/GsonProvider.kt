package net.kibotu.base

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import net.kibotu.base.GsonProvider.gson
import net.kibotu.base.GsonProvider.gsonPrettyPrinting
import net.kibotu.base.GsonProvider.jsonParser
import org.junit.Assert

object GsonProvider {

    val gson = GsonBuilder()
            .disableHtmlEscaping()
            .create()

    val gsonPrettyPrinting = GsonBuilder()
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create()

    val jsonParser = JsonParser()
}

fun assertEqualJson(expected: String?, actual: String?) {
    Assert.assertNotNull(expected, actual)
    Assert.assertEquals(jsonParser.parse(expected), jsonParser.parse(actual))
}

fun Any.toJson(): String = gson.toJson(this)

fun Any.toJsonPrettyPrinting(): String = gsonPrettyPrinting.toJson(this)