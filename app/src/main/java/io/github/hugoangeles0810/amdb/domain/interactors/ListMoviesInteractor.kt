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

package io.github.hugoangeles0810.amdb.domain.interactors

import io.github.hugoangeles0810.amdb.data.repositories.MovieRepository
import io.github.hugoangeles0810.amdb.domain.entities.Movie
import io.reactivex.Observable
import javax.inject.Inject

class ListMoviesInteractor
    @Inject constructor(private val movieRepository: MovieRepository) :
    Interactor<Any, Observable<List<Movie>>> {

    override fun execute(params: Any?): Observable<List<Movie>> {
        return movieRepository.listMovies()
    }

}