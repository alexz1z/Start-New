package com.alexz1z

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudstreamPlugin
class PrimeHubPlugin : Plugin() {
    override fun load(context: Context) {
        registerMainAPI(PrimeHub())
    }
}
