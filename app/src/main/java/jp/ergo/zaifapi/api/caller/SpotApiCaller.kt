package jp.ergo.zaifapi.api.caller

import io.reactivex.Single
import jp.ergo.zaifapi.api.entity.CurrenciesResult
import retrofit2.http.GET
import retrofit2.http.Path


interface SpotApiCaller {
    @GET("api/1/currencies/{currency}")
    fun getCurrencies(@Path("currency") id: String): Single<List<CurrenciesResult>>
}
