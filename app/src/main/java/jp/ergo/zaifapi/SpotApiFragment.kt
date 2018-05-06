package jp.ergo.zaifapi

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import jp.ergo.zaif.api.entity.*
import jp.ergo.zaif.api.service.DefaultSpotApiService


class SpotApiFragment private constructor() : Fragment() {
    enum class SpinnerElement {
        Currencies,
        CurrencyPairs,
        LastPrice,
        Ticker,
        Trades,
        Depth
    }

    companion object {
        fun newInstance(): SpotApiFragment {
            return SpotApiFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater?.inflate(R.layout.fragment_spot_api, null)

        val logTextView = root?.findViewById(R.id.logTextView) as TextView

        val spinner = root?.findViewById(R.id.spinner) as Spinner
        val adapter = ArrayAdapter(context,
                android.R.layout.simple_spinner_item, SpinnerElement.values())
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p0 is Spinner) {
                    when (p0.selectedItem as? SpinnerElement) {
                        SpinnerElement.Currencies -> {
                            DefaultSpotApiService().getCurrencies("all")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(object : DisposableSingleObserver<List<Currency>>(){
                                        override fun onSuccess(t: List<Currency>) {
                                            logTextView?.text = t.toString()
                                        }

                                        override fun onError(e: Throwable) {
                                        }

                                    })
                        }
                        SpinnerElement.CurrencyPairs -> {
                            DefaultSpotApiService().getCurrencyPairs("all")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(object : DisposableSingleObserver<List<CurrencyPair>>(){
                                        override fun onSuccess(t: List<CurrencyPair>) {
                                            logTextView?.text = t.toString()
                                        }

                                        override fun onError(e: Throwable) {
                                        }

                                    })
                        }
                        SpinnerElement.LastPrice -> {
                            DefaultSpotApiService().getLastPrice("btc_jpy")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(object : DisposableSingleObserver<LastPrice>(){
                                        override fun onSuccess(t: LastPrice) {
                                            logTextView?.text = t.toString()
                                        }

                                        override fun onError(e: Throwable) {
                                        }

                                    })
                        }
                        SpinnerElement.Ticker -> {
                            DefaultSpotApiService().getTicker("btc_jpy")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(object : DisposableSingleObserver<Ticker>(){
                                        override fun onSuccess(t: Ticker) {
                                            logTextView?.text = t.toString()
                                        }

                                        override fun onError(e: Throwable) {
                                        }

                                    })
                        }
                        SpinnerElement.Trades -> {
                            DefaultSpotApiService().getTrades("btc_jpy")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(object : DisposableSingleObserver<List<Trade>>(){
                                        override fun onSuccess(t: List<Trade>) {
                                            logTextView?.text = t.toString()
                                        }

                                        override fun onError(e: Throwable) {
                                        }

                                    })
                        }
                        SpinnerElement.Depth -> {
                            DefaultSpotApiService().getDepth("btc_jpy")
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeWith(object : DisposableSingleObserver<Depth>(){
                                        override fun onSuccess(t: Depth) {
                                            logTextView?.text = t.toString()
                                        }

                                        override fun onError(e: Throwable) {
                                        }

                                    })
                        }
                    }
                }
            }
        }

        return root
    }

}