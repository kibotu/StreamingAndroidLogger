package net.kibotu.model

import de.charite.balsam.base.SerializableTests

data class TestModel(var message: String? = null)

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
class ModelTest : SerializableTests(TestModel::class.java) {

    override val expectedJson: String = """
    {
        "message": "test"
    }
    """.trimMargin()
}