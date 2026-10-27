package com.alexz1z

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Element

class VegaMovies : MainAPI() {
    override var mainUrl = "https://new1.vegamovies.lease"
    override var name = "VegaMovies"
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
        val home = document.select("div.movies-grid > a, #moviesGridMain > a, a:has(.poster-card), article").mapNotNull {
            it.toSearchResult()
        }
        return newHomePageResponse(request.name, home)
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val href = if (this.tagName() == "a") {
            this.attr("href")
        } else {
            this.selectFirst("a")?.attr("href") ?: this.parents().firstOrNull { it.tagName() == "a" }?.attr("href")
        } ?: return null

        val fullHref = if (href.startsWith("http")) href else if (href.startsWith("/")) "$mainUrl$href" else "$mainUrl/$href"

        val img = this.selectFirst("img")
        val posterUrl = img?.attr("src")?.takeIf { it.isNotEmpty() } ?: img?.attr("data-src")?.takeIf { it.isNotEmpty() }

        val title = this.selectFirst(".poster-info, h2, h3, .entry-title")?.text()?.trim()?.takeIf { it.isNotEmpty() }
            ?: img?.attr("alt")?.trim()?.takeIf { it.isNotEmpty() }
            ?: return null

        return newMovieSearchResponse(title, fullHref, TvType.Movie) {
            this.posterUrl = posterUrl
        }
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val searchUrl = "$mainUrl/?s=$query"
        val document = app.get(searchUrl).document
        return document.select("div.movies-grid > a, #moviesGridMain > a, a:has(.poster-card), article").mapNotNull {
            it.toSearchResult()
        }
    }

    override suspend fun load(url: String): LoadResponse {
        val document = app.get(url).document
        val title = document.selectFirst("h1.entry-title, h1, .section-title")?.text()?.trim() ?: "VegaMovies"
        val poster = document.selectFirst("div.entry-content img, article img, .poster-image img")?.let { img ->
            val dataSrc = img.attr("data-src")
            if (dataSrc.isNotEmpty()) dataSrc else img.attr("src")
        }
        val description = document.selectFirst("div.entry-content p, .entry-content")?.text()?.trim()

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
        document.select("a[href*='download'], a[href*='vcloud'], a[href*='fastdl'], a.btn").forEach { link ->
            val href = link.attr("href")
            if (href.startsWith("http")) {
                loadExtractor(href, subtitleCallback, callback)
            }
        }
        return true
    }
}
