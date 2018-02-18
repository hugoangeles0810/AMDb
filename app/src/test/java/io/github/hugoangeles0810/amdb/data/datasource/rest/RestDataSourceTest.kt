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

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection

abstract class RestDataSourceTest {

    private val STUBS_PATH = "/stubs/"

    private lateinit var server: MockWebServer

    protected abstract fun setUp(baseUrl: String)

    protected abstract fun tearDown()

    @Before
    fun setUpServer() {
        server = MockWebServer()
        server.start()
        val baseUrl = server.url("")
        setUp(baseUrl.toString())
    }

    @After
    fun tearDownServer() {
        tearDown()
        server.shutdown()
    }

    protected fun enqueueResponse(code: Int, body: String, headers: Map<String, String>?) {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(code)
        mockResponse.setBody(body)

        if (headers != null) {
            val headerNames = headers.keys
            for (headerName in headerNames) {
                mockResponse.addHeader(headerName, headers[headerName])
            }
        }

        server.enqueue(mockResponse)
    }

    protected fun enqueueResponse(body: String) {
        enqueueResponse(HttpURLConnection.HTTP_OK, body, null)
    }

    protected fun enqueueResponseFromFile(statusCode: Int, filename: String, headers: Map<String, String>?) {
        try {
            val body = convertStreamToString(javaClass.getResourceAsStream(STUBS_PATH + filename))
            enqueueResponse(statusCode, body, headers)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun enqueueResponseFromFile(statusCode: Int, filename: String) {
        enqueueResponseFromFile(statusCode, filename, null)
    }

    protected fun enqueueResponseFromFile(filename: String) {
        enqueueResponseFromFile(HttpURLConnection.HTTP_OK, filename, null)
    }

    private fun convertStreamToString(input: InputStream): String {
        val reader = BufferedReader(InputStreamReader(input))
        val sb = StringBuilder()
        var line: String?
        line = reader.readLine()
        while (line != null) {
            sb.append(line).append("\n")
            line = reader.readLine()
        }
        reader.close()
        return sb.toString()
    }
}