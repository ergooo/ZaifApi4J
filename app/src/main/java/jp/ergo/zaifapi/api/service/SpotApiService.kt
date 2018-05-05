package jp.ergo.zaifapi.api.service

import io.reactivex.Single
import jp.ergo.zaifapi.api.ApiUtils
import jp.ergo.zaifapi.api.caller.SpotApiCaller
import jp.ergo.zaifapi.api.entity.CurrenciesResult


interface SpotApiService {
    fun getCurrencies(currency: String): Single<List<CurrenciesResult>>
}

class DefaultSpotApiService(private val baseUrl: String? = null, private val caller: SpotApiCaller = ApiUtils.createApiCaller(SpotApiCaller::class.java, baseUrl)) : SpotApiService {

    override fun getCurrencies(currency: String): Single<List<CurrenciesResult>> {
        return caller.getCurrencies(currency)
    }

}