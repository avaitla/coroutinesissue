package com.avaitla16.coroutinesissue

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import awaitObject
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.gson.gsonDeserializerOf
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MainActivity : AppCompatActivity() {

    data class UUID(val uuid: String)

    suspend fun getData(): Result<UUID, FuelError> {
        val awaitObject = "https://httpbin.org/uuid".httpGet().awaitObject (gsonDeserializerOf<UUID>())
        return awaitObject.third
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val makeRequestBtn = findViewById<Button>(R.id.request_btn)

        makeRequestBtn.setOnClickListener {
            makeRequest()
        }

    }

    fun makeRequest() = launch(UI) {
        val result = getData()

        when (result) {
            is Result.Failure -> {
                val ex = result.getException()
                Toast.makeText(this@MainActivity, "Failure: $ex", Toast.LENGTH_LONG).show()
            }

            is Result.Success -> {
                val data = result.get()
                Toast.makeText(this@MainActivity, "Success: $data", Toast.LENGTH_LONG).show()
            }
        }
    }

}
