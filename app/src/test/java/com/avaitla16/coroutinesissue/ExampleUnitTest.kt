package com.avaitla16.coroutinesissue

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.DefaultEnvironment
import com.github.kittinunf.fuel.core.FuelManager
import org.junit.Test

class ExampleUnitTest {
    init {
        // Required otherwise Fuel fails with 'Method getMainLooper in android.os.Looper not mocked'
        FuelManager.instance.callbackExecutor = DefaultEnvironment().callbackExecutor
        Fuel.testMode()
    }

    @Test
    fun addition_isCorrect() {
        Fuel.get("https://www.httpbin.org/uuid").responseString { _, _, msg ->
            println("Demonstrate Blocking by Sleeping...")
            Thread.sleep(5000)
            println("Woke from Sleeping...")

            assert(true)
            print("Assert True Passes")

            assert(false)
            print("Assert False Passes")
        }
    }
}
