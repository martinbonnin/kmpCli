package net.mbonnin.kmpcli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option

class MainCommand: CliktCommand("kmpCli") {
    val version by option().flag()
    override fun run() {
        if (version) {
            println("version is: 0-0-0-pre-alpha :)")
        }
    }
}

fun main(args: Array<String>) {
    println("program is starting")
    MainCommand().parse(args)
}