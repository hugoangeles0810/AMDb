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

import io.github.hugoangeles0810.amdb.R
import io.github.hugoangeles0810.amdb.data.exceptions.NetworkException
import io.github.hugoangeles0810.amdb.data.exceptions.ServerException
import io.github.hugoangeles0810.amdb.data.repositories.MovieRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable

class ListMoviesInteractorImpl(
        subscriberScheduler: Scheduler,
        observerScheduler: Scheduler,
        private val movieRepository: MovieRepository) :
        AbstractInteractor<ListMoviesInteractor.Callback, Any>(subscriberScheduler, observerScheduler),
        ListMoviesInteractor {

    override fun execute(callback: ListMoviesInteractor.Callback, params: Any): Disposable {
        return movieRepository
                .listMovies()
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .subscribe({ movies ->
                    if (movies.isNotEmpty()) {
                        callback.onSuccess(movies)
                    } else {
                        callback.onEmptyData()
                    }
                }, {
                    when(it) {
                        is NetworkException -> callback.onError(R.string.common_error_network)
                        is ServerException -> callback.onError(R.string.common_error_server)
                        else -> callback.onError(R.string.common_error_unknown)
                    }
                })
    }
}