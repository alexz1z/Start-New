package com.alexz1z

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class HDHub4uPlugin: Plugin() {
    override fun load(context: Context) {
        registerMainAPI(HDHub4u())
    }
}
