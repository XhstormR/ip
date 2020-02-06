package com.xhstormr.app

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @author zhangzf
 * @create 2020/2/5 13:21
 */
class IpUtilTests {

    @Test
    fun test() {
        // 获得起始 IP 和终止 IP（包含网络地址和广播地址）
        Assertions.assertEquals("59.154.192.0", IpUtil.getBeginIp("59.154.202.0/19"))
        Assertions.assertEquals("59.154.223.255", IpUtil.getEndIp("59.154.202.0/19"))

        // 根据起始 IP 和终止 IP，计算子网掩码
        Assertions.assertEquals(19, IpUtil.getCIDRNetmask("59.154.192.1", "59.154.223.254"))

        // 判断 IP 是否属于此 CIDR
        Assertions.assertTrue(IpUtil.isInCIDR("192.168.1.1", "192.168.1.0/24"))
        Assertions.assertFalse(IpUtil.isInCIDR("10.2.0.0", "10.3.0.0/17"))

        // 判断 IP 是否合法
        Assertions.assertTrue(IpUtil.isIP("10.2.0.0"))
        Assertions.assertFalse(IpUtil.isIP("10.3.0.999"))
        Assertions.assertFalse(IpUtil.isIP("10.0.0.a"))
    }
}
