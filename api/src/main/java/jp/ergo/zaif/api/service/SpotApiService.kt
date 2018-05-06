package jp.ergo.zaif.api.service

import io.reactivex.Single
import jp.ergo.zaif.api.entity.*
import jp.ergo.zaif.api.ApiUtils
import jp.ergo.zaif.api.caller.SpotApiCaller


interface SpotApiService {
    fun getCurrencies(currency: String): Single<List<Currency>>
    fun getCurrencyPairs(currencyPair: String): Single<List<CurrencyPair>>
    fun getLastPrice(currencyPair: String): Single<LastPrice>
    fun getTicker(currencyPair: String): Single<Ticker>
    fun getTrades(currencyPair: String): Single<List<Trade>>
    fun getDepth(currencyPair:String): Single<Depth>
}

class DefaultSpotApiService(private val baseUrl: String? = null, private val caller: SpotApiCaller = ApiUtils.createApiCaller(SpotApiCaller::class.java, baseUrl)) : SpotApiService {
    override fun getDepth(currencyPair: String): Single<Depth> {
        return caller.getDepth(currencyPair)
    }

    override fun getTrades(currencyPair: String): Single<List<Trade>> {
        return caller.getTrades(currencyPair)
    }

    override fun getTicker(currencyPair: String): Single<Ticker> {
        return caller.getTicker(currencyPair)
    }

    override fun getLastPrice(currencyPair: String): Single<LastPrice> {
        return caller.getLastPrice(currencyPair)
    }

    override fun getCurrencyPairs(currencyPair: String): Single<List<CurrencyPair>> {
        return caller.getCurrencyPairs(currencyPair)
    }

    override fun getCurrencies(currency: String): Single<List<Currency>> {
        return caller.getCurrencies(currency)
    }

}