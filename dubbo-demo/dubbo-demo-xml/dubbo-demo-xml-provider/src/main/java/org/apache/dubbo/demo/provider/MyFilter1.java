package org.apache.dubbo.demo.provider;

import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;

/**
 * @Author wangxi
 * @Time 2020/3/27 20:43
 */
public class MyFilter1 implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("调用之前："+invoker.getUrl().toFullString());
        Result result = invoker.invoke(invocation);
        System.out.println("调用之后："+invoker.getUrl().toFullString());
        return result;
    }
}
