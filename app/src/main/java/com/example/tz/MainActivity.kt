package com.example.tz


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
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
    lateinit var handler:Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkHelper = NetworkHelper(this)
        handler = Handler()
        handler.postDelayed(runnable,1000)
        requestQueue = Volley.newRequestQueue(this)


        myIp.setOnClickListener {
            if(networkHelper.isNetworkConnected()) {
                if (txt.text.toString().isNotEmpty()) {
                    fetchIpLoad()
                } else {
                    Toast.makeText(this, "Link is empty", Toast.LENGTH_SHORT).show()
                } 
            } else {
                Toast.makeText(this, "Network is unconnection, please check again...", Toast.LENGTH_SHORT).show()
            }

        }
    }

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


    val runnable = object : Runnable {
        override fun run() {
            networkHelper = NetworkHelper(this@MainActivity)
            if (networkHelper.isNetworkConnected()) {
                txt_connection.text = "Connected"
                Log.d("NETWORK", "Connected")
            } else {
               txt_connection.text = "Disconnected"
                Log.d("NETWORK", "Disconnected")
            }

            handler.postDelayed(this, 1000)
        }
    }

}