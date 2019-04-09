package net.kibotu.logger

import android.app.Application
import com.github.florent37.application.provider.ProviderInitializer
import net.kibotu.ContextHelper

class ContextProvider : ProviderInitializer() {

    override fun initialize(): (Application) -> Unit = {
        ContextHelper.with(it)
    }
}