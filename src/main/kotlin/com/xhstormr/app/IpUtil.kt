package com.xhstormr.app

/**
 * @author zhangzf
 * @create 2020/2/5 13:21
 */
object IpUtil {

    private const val IP_PATTERN = "" +
            """([01]?\d\d?|2[0-4]\d|25[0-5])\.""" +
            """([01]?\d\d?|2[0-4]\d|25[0-5])\.""" +
            """([01]?\d\d?|2[0-4]\d|25[0-5])\.""" +
            """([01]?\d\d?|2[0-4]\d|25[0-5])"""

    private val IP_REGEX = IP_PATTERN.toRegex()

    /**
     * 判断 IP 是否合法
     */
    fun isIP(ip: String) = IP_REGEX.matches(ip)

    /**
     * 根据 CIDR 的起始 IP 和终止 IP，计算子网掩码
     */
    fun getCIDRNetmask(ip1: String, ip2: String): Int {
        val iplong1 = ip2long(ip1)
        val iplong2 = ip2long(ip2)
        val netmask = netmask2int(long2ip(iplong1 xor iplong2))
        return 32 - netmask
    }

    /**
     * 判断 IP 是否属于此 CIDR
     */
    fun isInCIDR(ip: String, cidr: String): Boolean {
        val iplong1 = ip2long(ip)

        val list = cidr.split('/')
        val iplong2 = ip2long(list[0])
        val netmask = list[1].toInt()

        val mask = (-1L).shl(32 - netmask)
        return iplong1 and mask == iplong2 and mask
    }

    /**
     * 计算 CIDR 的起始 IP
     */
    fun getBeginIp(cidr: String): String {
        val list = cidr.split('/')
        val ip = list[0]
        val netmask = list[1].toInt()
        return long2ip(ip2long(ip).and(ip2long(int2netmask(netmask))))
    }

    /**
     * 计算 CIDR 的终止 IP
     */
    fun getEndIp(cidr: String): String {
        val list = cidr.split('/')
        val ip = list[0]
        val netmask = list[1].toInt()
        return long2ip(ip2long(ip).and(ip2long(int2netmask(netmask))) +
                ip2long(int2netmask(netmask)).inv())
    }

    /**
     * long -> ip
     */
    fun long2ip(ip: Long): String {
        val sb = StringBuilder()
        for (i in 3 downTo 0) {
            sb.append('.')
                    .append(ip.shr(i * 8).and(0xFF))
        }
        return sb.substring(1)
    }

    /**
     * ip -> long
     */
    fun ip2long(ip: String) = ip
            .split('.')
            .map { it.toLong() }
            .reduce { acc, l -> acc.shl(8).or(l) }

    /**
     * int -> netmask
     */
    fun int2netmask(netmask: Int): String {
        val sb = StringBuilder()
        var j = netmask
        repeat(4) {
            val i = if (j < 8) j else 8
            val bit = "1".repeat(i) + "0".repeat(8 - i)
            j -= i
            sb.append('.')
                    .append(Integer.valueOf(bit, 2))
        }
        return sb.substring(1)
    }

    /**
     * netmask -> int
     */
    fun netmask2int(netmask: String) = netmask
            .split('.')
            .map { it.toInt() }
            .map { Integer.bitCount(it) }
            .sum()
}
