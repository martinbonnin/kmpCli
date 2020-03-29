package net.mbonnin.kmpcli

import kotlinx.coroutines.*
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
    val queue = dispatch_get_main_queue()
    println("dispatch")
    dispatch_async(queue) {
        println("Hello Martin")
    }

    println("calling dispatch_main")
    dispatch_main()
}