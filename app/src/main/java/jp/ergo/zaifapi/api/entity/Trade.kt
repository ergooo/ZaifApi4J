package jp.ergo.zaifapi.api.entity


/**
date	取引日時	UNIX_TIMESTAMP
price	取引価格	float
amount	取引量	float
tid	取引ID	int
currency_pair	通貨ペア	str
trade_type	取引種別	str
 */
data class Trade(
        val date: Long,
        val price: Float,
        val amount: Float,
        val tid: Int,
        val currencyPair: String,
        val tradeType: String
)