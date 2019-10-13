package mytest.spi;

/**
 * @Author wangxi
 * @Time 2019/10/12 00:02
 */
public class WhitePerson implements Person{
    @Override
    public void sayHello() {
        System.out.println("white person");
    }
}
