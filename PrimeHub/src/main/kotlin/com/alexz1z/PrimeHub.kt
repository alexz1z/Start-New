package com.alexz1z

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element

class PrimeHub : MainAPI() {
    override var mainUrl = "https://xprimehub.pics"
    override var name = "PrimeHub"
    override val hasMainPage = true
    override var lang = "en"
    override val hasQuickSearch = false
    override val supportedTypes = setOf(
        TvType.Movie,
        TvType.TvSeries
    )

    override val mainPage = mainPageOf(
        "$mainUrl/page/" to "Latest Releases"
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val url = if (page <= 1) mainUrl else "${request.data}$page/"
        val document = app.get(url).document
        val home = document.select("article, div.post-item, div.item, .thumb-item, .blog-card").mapNotNull {
            it.toSearchResult()
        }
        return newHomePageResponse(request.name, home)
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val title = this.selectFirst("h2, h3, .entry-title, .title")?.text()?.trim() ?: return null
        val href = this.selectFirst("a")?.attr("href") ?: return null
        val posterUrl = this.selectFirst("img")?.let { img ->
            val dataSrc = img.attr("data-src")
            val dataLazy = img.attr("data-lazy-src")
            when {
                dataSrc.isNotEmpty() -> dataSrc
                dataLazy.isNotEmpty() -> dataLazy
                else -> img.attr("src")
            }
        }

        return newMovieSearchResponse(title, href, TvType.Movie) {
            this.posterUrl = posterUrl
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val searchUrl = "$mainUrl/?s=$query"
        val document = app.get(searchUrl).document
        return document.select("article, div.post-item, div.item, .thumb-item, .blog-card").mapNotNull {
            it.toSearchResult()
        }
    }

    override suspend fun load(url: String): LoadResponse {
        val document = app.get(url).document
        val title = document.selectFirst("h1.entry-title, h1, .title")?.text()?.trim() ?: "PrimeHub"
        val poster = document.selectFirst("div.entry-content img, article img, .poster img")?.let { img ->
            val dataSrc = img.attr("data-src")
            val dataLazy = img.attr("data-lazy-src")
            when {
                dataSrc.isNotEmpty() -> dataSrc
                dataLazy.isNotEmpty() -> dataLazy
                else -> img.attr("src")
            }
        }
        val description = document.selectFirst("div.entry-content p, .plot, .description")?.text()?.trim()

        return newMovieLoadResponse(title, url, TvType.Movie, url) {
            this.posterUrl = poster
            this.plot = description
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val document = app.get(data).document
        document.select("a[href*='download'], a[href*='drive'], a[href*='fastdl'], a[href*='vcloud'], a.btn").forEach { link ->
            val href = link.attr("href")
            if (href.startsWith("http")) {
                loadExtractor(href, subtitleCallback, callback)
            }
        }
        return true
    }
}
