package com.alexz1z

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudstreamPlugin
class RogMoviesPlugin: Plugin() {
    override fun load(context: Context) {
        registerMainAPI(RogMovies())
    }
}
