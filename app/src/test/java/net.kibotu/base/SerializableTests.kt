package de.charite.balsam.base

import net.kibotu.base.BaseTest
import net.kibotu.base.GsonProvider.gson
import net.kibotu.base.assertEqualJson
import net.kibotu.base.toJsonPrettyPrinting
import org.junit.Assert
import org.junit.Test

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

abstract class SerializableTests(var klass: Class<*>) : BaseTest() {

    open val expectedJson: String = ""

    @Test
    open fun serialize() {
        val actual = gson.fromJson(expectedJson, klass)
        Assert.assertNotNull(actual)
    }

    @Test
    open fun deserialize() {
        val actual = gson.fromJson(expectedJson, klass)
        Assert.assertNotNull(actual)

        val actualJson = actual.toJsonPrettyPrinting()
        assertEqualJson(expectedJson, actualJson)
    }
}