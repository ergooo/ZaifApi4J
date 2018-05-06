package jp.ergo.zaif.api.service

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import jp.ergo.zaif.api.entity.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.apache.commons.io.IOUtils
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.hamcrest.core.Is.`is` as be

@RunWith(JUnit4::class)
class DefaultSpotApiServiceTest {
    private var server: MockWebServer? = null

    @Before
    fun setUp() {
        server = MockWebServer()
    }

    @After
    fun tearDown() {
        server?.shutdown()
    }

    @Test
    fun getCurrencies() {
        val mockResponse = MockResponse()
                .setBody("[{\"token_id\": null, \"id\": 1, \"is_token\": false, \"name\": \"btc\"}]")
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
        server?.enqueue(mockResponse)

        val sut = DefaultSpotApiService(server?.url("/").toString())
        val result = getResult(sut.getCurrencies("btc"))

        val expect = Currency("btc", false)
        assertThat(result[0], be(expect))
    }

    @Test
    fun getCurrencyPairs() {
        val mockResponse = MockResponse()
                .setBody("""[{"aux_unit_min": 5.0, "item_japanese": "\u30d3\u30c3\u30c8\u30b3\u30a4\u30f3", "name": "BTC/JPY", "event_number": 0, "item_unit_min": 0.0001, "item_unit_step": 0.0001, "is_token": false, "seq": 0, "id": 1, "aux_unit_point": 0, "aux_japanese": "\u65e5\u672c\u5186", "title": "BTC/JPY", "description": "\u30d3\u30c3\u30c8\u30b3\u30a4\u30f3\u30fb\u65e5\u672c\u5186\u306e\u53d6\u5f15\u3092\u884c\u3046\u3053\u3068\u304c\u3067\u304d\u307e\u3059", "aux_unit_step": 5.0, "currency_pair": "btc_jpy"}]""")
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
        server?.enqueue(mockResponse)

        val sut = DefaultSpotApiService(server?.url("/").toString())
        val result = getResult(sut.getCurrencyPairs("btc_jpy"))

        val expect = CurrencyPair(
                "BTC/JPY",
                "BTC/JPY",
                "btc_jpy",
                "ビットコイン・日本円の取引を行うことができます",
                false,
                0,
                0,
                1.0E-4f,
                1.0E-4f,
                "ビットコイン",
                5.0f,
                5.0f,
                0,
                "日本円"
        )
        assertThat(result[0], be(expect))
    }

    @Test
    fun getLastPrice() {
        val mockResponse = MockResponse()
                .setBody("""{"last_price": 1076000.0}""")
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
        server?.enqueue(mockResponse)

        val sut = DefaultSpotApiService(server?.url("/").toString())
        val result = getResult(sut.getLastPrice("btc_jpy"))

        val expect = LastPrice(1076000.0f)

        assertThat(result, be(expect))
    }

    @Test
    fun getTicker() {
        val mockResponse = MockResponse()
                .setBody("""{"last": 1075800.0, "high": 1082000.0, "low": 1048200.0, "vwap": 1061702.5233, "volume": 8832.4729, "bid": 1075800.0, "ask": 1075895.0}""")
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
        server?.enqueue(mockResponse)
        val sut = DefaultSpotApiService(server?.url("/").toString())
        val result = getResult(sut.getTicker("btc_jpy"))
        val expect = Ticker(1075800.0f, 1082000.0f, 1048200.0f, 1061702.5233f, 8832.4729f, 1075800.0f, 1075895.0f)

        assertThat(result, be(expect))
    }

    @Test
    fun getTrades() {
        val testData = IOUtils.toString(
                this::class.java.getResourceAsStream("/trades.json"),
                "UTF-8"
        )

        val mockResponse = MockResponse()
                .setBody(testData)
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
        server?.enqueue(mockResponse)
        val sut = DefaultSpotApiService(server?.url("/").toString())
        val result = getResult(sut.getTrades("btc_jpy"))

        val expect = Trade(1525523599, 1079000.0f, 0.05f, 114441560, "btc_jpy", "ask")
        assertThat(result[0], be(expect))
    }

    @Test
    fun getDepth() {
        val testData = IOUtils.toString(
                this::class.java.classLoader.getResourceAsStream("depth.json"),
                "UTF-8"
        )

        val mockResponse = MockResponse()
                .setBody(testData)
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
        server?.enqueue(mockResponse)
        val sut = DefaultSpotApiService(server?.url("/").toString())
        val result = getResult(sut.getDepth("btc_jpy"))

        val expectAsk = Depth.Element(1078500.0f, 0.0925f)
        val expectBid = Depth.Element(1078170.0f, 0.3102f)
        assertThat(result.asks[0], be(expectAsk))
        assertThat(result.bids[0], be(expectBid))
    }

    private fun <T> getResult(single: Single<T>): T {
        return single.subscribeOn(Schedulers.io())
                .test()
                .await()
                .assertNoErrors()
                .values()[0]
    }

}