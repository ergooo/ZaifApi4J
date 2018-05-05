package jp.ergo.zaifapi.api.entity


/**
last	終値	float
high	過去24時間の高値	float
low	過去24時間の安値	float
vwap	過去24時間の加重平均	float
volume	過去24時間の出来高	float
bid	買気配値	float
ask	売気配値	float
 */
data class Ticker(
        val last: Float,
        val high: Float,
        val low: Float,
        val vwap: Float,
        val volume: Float,
        val bid: Float,
        val ask: Float)