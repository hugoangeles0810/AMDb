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

package io.github.hugoangeles0810.amdb.data.model.rest

import com.google.gson.annotations.SerializedName
import java.util.*

data class Configuration(
        @SerializedName("images")
        val imageConfig: ImageConfig
) {
    data class ImageConfig(
            @SerializedName("base_url")
            val baseUrl: String,
            @SerializedName("backdrop_sizes")
            val backdropSizes: Array<String>,
            @SerializedName("poster_sizes")
            val posterSizes: Array<String>) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ImageConfig

            if (baseUrl != other.baseUrl) return false
            if (!Arrays.equals(backdropSizes, other.backdropSizes)) return false
            if (!Arrays.equals(posterSizes, other.posterSizes)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = baseUrl.hashCode()
            result = 31 * result + Arrays.hashCode(backdropSizes)
            result = 31 * result + Arrays.hashCode(posterSizes)
            return result
        }
    }
}