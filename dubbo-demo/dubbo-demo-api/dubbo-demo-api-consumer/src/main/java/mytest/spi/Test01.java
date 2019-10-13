package mytest.spi;

import java.util.ServiceLoader;

/**
 * @Author wangxi
 * @Time 2019/10/11 23:59
 *
 * 测试jdk的SPI机制
 * 需要把Person类的 @SPI注解去掉(这个是测试dubbo SPI使用)
 * 然后把services/2mytest.spi.Person  改为mytest.spi.Person
 */
public class Test01 {
    public static void main(String[] args) {
        // 默认加载了所有的实现类，是一种浪费
        ServiceLoader<Person> serviceLoader = ServiceLoader.load(Person.class);
        serviceLoader.forEach(Person::sayHello);
    }
}
