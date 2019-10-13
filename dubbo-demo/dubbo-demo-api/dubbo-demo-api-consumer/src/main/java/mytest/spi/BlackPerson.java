package mytest.spi;

/**
 * @Author wangxi
 * @Time 2019/10/12 00:02
 */
public class BlackPerson implements Person{
    @Override
    public void sayHello() {
        System.out.println("black person");
    }
}
