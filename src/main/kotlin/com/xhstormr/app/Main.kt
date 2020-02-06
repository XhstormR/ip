package com.xhstormr.app

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

/**
 * @author zhangzf
 * @create 2020/2/5 15:21
 */
fun main() {
    val range2cidr: (String) -> String = {
        val list = it.split('-')
        val mask = IpUtil.getCIDRNetmask(list[0], list[1])
        list[0] + '/' + mask
    }

    val list = File("assets")
            .resolve("in.txt")
            .readLines()
            .map(range2cidr)

    Path.of("assets")
            .resolve("out.txt")
            .let { Files.write(it, list) }
}
