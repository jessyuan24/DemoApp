package com.coldwizards.demoapp

import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun derived_test() {
        val b = BaseImpl(2)
        Derived(b).print()
    }

    @Test
    fun split_test() {
        val str = "asdfasdf"
        println(str.split(",")[0])
    }

    @Test
    fun set_json_test() {
        val sets = hashSetOf("asdf", "11111")
        val json = Gson().toJson(sets)
    }
}

interface Base {
    fun print()
}

class BaseImpl(val x: Int): Base {
    override fun print() {
        print(x * x)
    }
}

class Derived(b: Base): Base by b {

}
