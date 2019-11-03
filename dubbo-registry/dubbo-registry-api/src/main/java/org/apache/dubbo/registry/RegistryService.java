/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.registry;

import org.apache.dubbo.common.URL;

import java.util.List;

/**
 * RegistryService. (SPI, Prototype, ThreadSafe)
 *
 * @see org.apache.dubbo.registry.Registry
 * @see org.apache.dubbo.registry.RegistryFactory#getRegistry(URL)
 * 注册中心模块的服务接口，提供了注册、取消注册、订阅、取消订阅以及查询符合条件的已注册数据
 */
public interface RegistryService {

    /**
     * Register data, such as : provider service, consumer address, route rule, override rule and other data.
     * <p>
     * Registering is required to support the contract:<br>
     * 1. When the URL sets the check=false parameter. When the registration fails, the exception is not thrown and retried in the background. Otherwise, the exception will be thrown.<br>
     * 2. When URL sets the dynamic=false parameter, it needs to be stored persistently, otherwise, it should be deleted automatically when the registrant has an abnormal exit.<br>
     * 3. When the URL sets category=routers, it means classified storage, the default category is providers, and the data can be notified by the classified section. <br>
     * 4. When the registry is restarted, network jitter, data can not be lost, including automatically deleting data from the broken line.<br>
     * 5. Allow URLs which have the same URL but different parameters to coexist,they can't cover each other.<br>
     *
     * @param url  Registration information , is not allowed to be empty, e.g: dubbo://10.20.153.10/org.apache.dubbo.foo.BarService?version=1.0.0&application=kylin
     */
    void register(URL url);

    /**
     * Unregister 取消注册
     * <p>
     * Unregistering is required to support the contract:<br>
     * 1. If it is the persistent stored data of dynamic=false, the registration data can not be found, then the IllegalStateException is thrown, otherwise it is ignored.<br>
     *     如果是dynamic=false的持久存储数据，找不到注册数据，则抛IllegalStateException，否则忽略。<br>
     * 2. Unregister according to the full url match.<br>
     *
     * @param url Registration information , is not allowed to be empty, e.g: dubbo://10.20.153.10/org.apache.dubbo.foo.BarService?version=1.0.0&application=kylin
     */
    void unregister(URL url);

    /**
     * 订阅服务.
     *
     * 订阅需处理契约：<br>
     * 1. 当URL设置了check=false时，订阅失败后不报错，在后台定时重试。<br>
     * 2. 当URL设置了category=overrides，只通知指定分类的数据，多个分类用逗号分隔，并允许星号通配，表示订阅所有分类数据。<br>
     * 3. 允许以interface,group,version,classifier作为条件查询，如：interface=com.alibaba.foo.BarService&version=1.0.0<br>
     * 4. 并且查询条件允许星号通配，订阅所有接口的所有分组的所有版本，或：interface=*&group=*&version=*&classifier=*<br>
     * 5. 当注册中心重启，网络抖动，需自动恢复订阅请求。<br>
     * 6. 允许URI相同但参数不同的URL并存，不能覆盖。<br>
     * 7. 必须阻塞订阅过程，等第一次通知完后再返回。<br>
     *
     * @param url 订阅条件，不允许为空，如：consumer://10.20.153.10/com.alibaba.foo.BarService?version=1.0.0&application=kylin
     * @param listener 变更事件监听器，不允许为空
     */
    void subscribe(URL url, NotifyListener listener);

    /**
     * 取消订阅服务.
     *
     * 取消订阅需处理契约：<br>
     * 1. 如果没有订阅，直接忽略。<br>
     * 2. 按全URL匹配取消订阅。<br>
     *
     * @param url 订阅条件，不允许为空，如：consumer://10.20.153.10/com.alibaba.foo.BarService?version=1.0.0&application=kylin
     * @param listener 变更事件监听器，不允许为空
     */
    void unsubscribe(URL url, NotifyListener listener);

    /**
     * 查询注册列表，与订阅的推模式相对应，这里为拉模式，只返回一次结果，通过url进行条件查询所匹配的所有URL集合。
     *
     * @see org.apache.dubbo.registry.NotifyListener#notify(List)
     * @param url 查询条件，不允许为空，如：consumer://10.20.153.10/com.alibaba.foo.BarService?version=1.0.0&application=kylin
     * @return 已注册信息列表，可能为空，含义同{@link org.apache.dubbo.registry.NotifyListener#notify(List<URL>)}的参数。
     */
    List<URL> lookup(URL url);

}