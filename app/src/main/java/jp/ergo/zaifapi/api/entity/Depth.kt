package jp.ergo.zaifapi.api.entity

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type


data class Depth(val asks: List<Element>, val bids: List<Element>) {


    data class Element(val price: Float, val amount: Float) {
        companion object {
            val adapter = (Element::class.java to DepthElementDeserializer())
        }

        class DepthElementDeserializer : JsonDeserializer<Element> {
            override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Element {
                val jsonArray = json!!.asJsonArray
                return Element(jsonArray[0].asFloat, jsonArray[1].asFloat)
            }
        }
    }
}
