package com.example.tz


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import utils.NetworkHelper
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    lateinit var networkHelper: NetworkHelper
    lateinit var requestQueue: RequestQueue

    override fun onResume() {
        super.onResume()
        if (networkHelper.isNetworkConnected()) {
            txt_connection.text = "Connected"

            requestQueue = Volley.newRequestQueue(this)

        } else {
            txt_connection.text = "Disconnected"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkHelper = NetworkHelper(this)


        myIp.setOnClickListener {
            fetchIpLoad()

        }
    }
/*
    private fun isNetworkConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            //SDK over the  23

            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

            return networkCapabilities != null && networkCapabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            )
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo

            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }*/


    private fun fetchIpLoad() {
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.GET,
                txt.text.toString(),
                null,
                object : Response.Listener<JSONObject> {
                    override fun onResponse(response: JSONObject?) {
                        val strstring = response?.getString("ip")
                        txt2.setText(strstring)
                    }
                },
                object : Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError?) {
                        txt2.setText(error?.message)
                    }

                })

        requestQueue.add(jsonObjectRequest)
        txt.text.clear()
    }
}