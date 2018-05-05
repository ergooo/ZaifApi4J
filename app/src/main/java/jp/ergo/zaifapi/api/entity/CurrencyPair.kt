package jp.ergo.zaifapi.api.entity

/**
name	通貨ペアの名前	str
title	通貨ペアのタイトル	str
currency_pair	通貨ペアのシステム文字列	str
description	通貨ペアの詳細	str
is_token	token種別	boolean
event_number	イベントトークンの場合、0以外	int
seq	通貨シークエンス	int
item_unit_min	アイテム通貨最小値	float
item_unit_step	アイテム通貨入力単位	float
item_japanese	アイテム通貨 日本語表記	str
aux_unit_min	相手通貨最小値	float
aux_unit_step	相手通貨入力単位	float
aux_unit_point	相手通貨小数点	int
aux_japanese	相手通貨 日本語表記	str
 */
data class CurrencyPair(
        val name: String,
        val title: String,
        val currencyPair: String,
        val description: String,
        val isToken: Boolean,
        val eventNumber: Int,
        val seq: Int,
        val itemUnitMin: Float,
        val itemUnitStep: Float,
        val itemJapanese: String,
        val auxUnitMin: Float,
        val auxUnitStep: Float,
        val auxUnitPoint: Int,
        val auxJapanese: String)