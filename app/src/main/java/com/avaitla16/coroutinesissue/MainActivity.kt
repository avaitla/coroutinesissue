package com.avaitla16.coroutinesissue

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import awaitString
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val makeRequestBtn = findViewById<Button>(R.id.request_btn)

        makeRequestBtn.setOnClickListener {
            printRemoteDataV1()

            printRemoteDataV2()

            printRemoteDataV3()

            printRemoteDataV4()
        }

    }

    fun toastMessage(string: String) {
        Toast.makeText(this@MainActivity, string, Toast.LENGTH_LONG).show()
    }



    fun printRemoteDataV1() {
        val job = async(CommonPool) { getDataFromWebV1() }

        launch(UI) {
            val result = job.await()
            toastMessage("V1: $result")
        }
    }

    suspend fun getDataFromWebV1(): String {
        return "https://httpbin.org/uuid".httpGet().awaitString()
    }



    fun printRemoteDataV2() = launch(CommonPool) {
        val result = getDataFromWebV2()

        launch(UI) {
            toastMessage("V2: $result")
        }
    }

    suspend fun getDataFromWebV2(): String {
        return "https://httpbin.org/uuid".httpGet().awaitString()
    }



    fun printRemoteDataV3() {
        launch(UI) {
            val result = getDataFromWebV3()
            toastMessage("V3: $result")
        }
    }

    suspend fun getDataFromWebV3(): String = withContext(CommonPool) {
        "https://httpbin.org/uuid".httpGet().awaitString()
    }



    fun printRemoteDataV4() {
        launch(UI) {
            val result = getDataFromWebV4().await()
            toastMessage("V4: $result")
        }
    }

    fun getDataFromWebV4(): Deferred<String> {
        return async(CommonPool) {
            "https://httpbin.org/uuid".httpGet().awaitString()
        }
    }



}
