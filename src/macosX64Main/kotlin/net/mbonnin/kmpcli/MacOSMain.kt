package net.mbonnin.kmpcli

import io.ktor.client.HttpClient
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.*
import platform.CoreFoundation.CFRunLoopRun
import platform.darwin.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(InternalCoroutinesApi::class)
val mainDispatcher: CoroutineDispatcher = object : CoroutineDispatcher(), Delay {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        val queue = dispatch_get_main_queue()
        println("dispatch")
        dispatch_async(queue) {
            println("dispatched")
            block.run()
        }
    }

    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        val queue = dispatch_get_main_queue()

        val time = dispatch_time(DISPATCH_TIME_NOW, (timeMillis * NSEC_PER_MSEC.toLong()))
        println("dispatchAfteer")
        dispatch_after(time, queue) {
            println("dispatchedAfter")
            with(continuation) {
                resumeUndispatched(Unit)
            }
        }
    }
}


actual fun macOSMain() {
    GlobalScope.launch(mainDispatcher) {
        val response = HttpClient {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
        }.get<HttpResponse>("https://google.com/") {

        }
        println("response status is: ${response.status}")
    }
    CFRunLoopRun()
}