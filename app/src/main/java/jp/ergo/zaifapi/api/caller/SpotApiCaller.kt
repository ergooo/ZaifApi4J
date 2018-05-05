package jp.ergo.zaifapi.api.caller

import io.reactivex.Single
import jp.ergo.zaifapi.api.entity.*
import retrofit2.http.GET
import retrofit2.http.Path


interface SpotApiCaller {
    @GET("api/1/currencies/{currency}")
    fun getCurrencies(@Path("currency") currency: String): Single<List<Currency>>

    @GET("api/1/currency_pairs/{currencyPair}")
    fun getCurrencyPairs(@Path("currencyPair") currencyPair: String): Single<List<CurrencyPair>>

    @GET("api/1/last_price/{currencyPair}")
    fun getLastPrice(@Path("currencyPair") currencyPair: String): Single<LastPrice>

    @GET("api/1/ticker/{currencyPair}")
    fun getTicker(@Path("currencyPair") currencyPair: String): Single<Ticker>

    @GET("api/1/trades/{currencyPair}")
    fun getTrades(@Path("currencyPair") currencyPair: String): Single<List<Trade>>

   @GET("api/1/depth/{currencyPair}")
    fun getDepth(@Path("currencyPair") currencyPair: String): Single<Depth>
}
