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

package io.github.hugoangeles0810.amdb.data.mapper

import io.github.hugoangeles0810.amdb.data.model.rest.DiscoverMoviesResponse
import io.github.hugoangeles0810.amdb.data.model.rest.MovieItemResponse
import io.github.hugoangeles0810.amdb.domain.entities.Movie

class DiscoverMoviesResponseMapper {

    fun transform(discoverMoviesResponse: DiscoverMoviesResponse): List<Movie> {
        val movieItemResponseMapper = MovieItemResponseMapper()
        return discoverMoviesResponse.results.map { movieItemResponseMapper.transform(it) }
    }
}

class MovieItemResponseMapper {

    fun transform(itemResponse: MovieItemResponse): Movie {
        return Movie(
                itemResponse.id,
                itemResponse.title,
                itemResponse.overview,
                itemResponse.popularity,
                itemResponse.posterPath
        )
    }
}