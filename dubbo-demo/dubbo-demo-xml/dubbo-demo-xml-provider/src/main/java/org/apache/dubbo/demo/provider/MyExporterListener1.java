package org.apache.dubbo.demo.provider;

import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.ExporterListener;
import org.apache.dubbo.rpc.RpcException;

/**
 * @Author wangxi
 * @Time 2020/3/27 20:31
 */
public class MyExporterListener1 implements ExporterListener {
    @Override
    public void exported(Exporter<?> exporter) throws RpcException {
        System.out.println("自定义export listener");
    }

    @Override
    public void unexported(Exporter<?> exporter) {

    }
}
