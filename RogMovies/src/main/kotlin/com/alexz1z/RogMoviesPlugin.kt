package com.alexz1z

import android.content.Context
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin

@CloudstreamPlugin
class RogMoviesPlugin: Plugin() {
    override fun load(context: Context) {
        registerMainAPI(RogMovies())
    }
}
