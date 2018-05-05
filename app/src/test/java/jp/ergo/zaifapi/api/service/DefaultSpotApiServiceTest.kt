package jp.ergo.zaifapi.api.service

import io.reactivex.schedulers.Schedulers
import jp.ergo.zaifapi.api.entity.CurrenciesResult
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Test
import org.hamcrest.core.Is.`is` as be
class DefaultSpotApiServiceTest {
    private var server: MockWebServer? = null

    @After
    fun tearDown() {
        server?.shutdown()
    }
    @Test
    fun getCurrencies() {
        server = MockWebServer()

        val mockResponse = MockResponse()
                .setBody("[{\"token_id\": null, \"id\": 1, \"is_token\": false, \"name\": \"btc\"}]")
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
        server?.enqueue(mockResponse)

        val sut = DefaultSpotApiService(server?.url("/").toString())
        val result = sut.getCurrencies("btc")
                .subscribeOn(Schedulers.io())
                .test()
                .await()
                .assertNoErrors()
                .values()[0]

        val expect = CurrenciesResult("btc", false)
        assertThat(result[0], be(expect))

    }

}