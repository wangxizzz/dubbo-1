package mytest.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * @Author wangxi
 * @Time 2019/10/12 00:01
 */
@SPI
public interface Person {
    void sayHello();
}
