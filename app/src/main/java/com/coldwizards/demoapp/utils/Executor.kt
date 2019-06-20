package com.coldwizards.demoapp.utils

import java.util.concurrent.Executors

/**
 * Created by jess on 19-6-13.
 */
private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(run :() -> Unit) {
    IO_EXECUTOR.execute(run)
}