/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Hugo Angeles
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package io.github.hugoangeles0810.amdb.data.datasource.rest

import io.github.hugoangeles0810.amdb.data.datasource.MovieDataSource
import io.github.hugoangeles0810.amdb.data.datasource.rest.api.ApiService
import io.github.hugoangeles0810.amdb.domain.entities.Movie
import io.reactivex.observers.TestObserver
import junit.framework.Assert.*
import org.junit.Test
import javax.inject.Inject

class MovieRestDataSourceTest : RestDataSourceTest() {

    @Inject
    protected lateinit var apiService: ApiService

    private lateinit var movieDataSource: MovieDataSource

    override fun setUp(baseUrl: String) {
        DaggerApiComponentTest.builder()
                .apiModule(ApiModuleTest(baseUrl))
                .build()
                .injectTo(this)

        movieDataSource = MovieRestDataSource(apiService)
        assertNotNull(movieDataSource)

    }

    override fun tearDown() {
       // Tear down resources here
    }

    @Test
    fun testListMovies() {
        enqueueResponseFromFile("discover_movies_ok.json")

        val testObserver = TestObserver<List<Movie>>()
        testObserver.assertNotSubscribed()

        movieDataSource.list().subscribe(testObserver)

        testObserver
                .assertSubscribed()
                .assertNoErrors()
                .assertValue { it.isNotEmpty() }
                .assertValue { isFirstExpectedMovie(it.first()) }
    }

    private fun isFirstExpectedMovie(movie: Movie): Boolean {
        assertEquals("211672", movie.id)
        assertEquals("Minions", movie.title)
        assertEquals("/q0R4crx2SehcEEQEkYObktdeFy.jpg", movie.posterUrl)
        return true
    }
}