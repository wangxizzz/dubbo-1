package mytest.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @Author wangxi
 * @Time 2019/10/12 00:10
 * 测试dubbo的SPI
 */
public class DubboSPITest {
    public static void main(String[] args) {
        // 指定加载哪个
        ExtensionLoader<Person> extensionLoader =
                ExtensionLoader.getExtensionLoader(Person.class);
//        Person optimusPrime = extensionLoader.getExtension("blackPerson");
//        optimusPrime.sayHello();
        Person bumblebee = extensionLoader.getExtension("whitePerson");
        bumblebee.sayHello();
    }
}
