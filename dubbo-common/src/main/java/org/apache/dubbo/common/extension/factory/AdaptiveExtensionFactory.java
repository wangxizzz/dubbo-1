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
package org.apache.dubbo.common.extension.factory;

import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.ExtensionFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AdaptiveExtensionFactory
 * 该类是ExtensionFactory的适配器类，也就是注解@Adaptive中适配器类的使用.
 * 当@Adaptive打在类上的情况
 */
@Adaptive
public class AdaptiveExtensionFactory implements ExtensionFactory {
    // 相当于组合了所有 ExtensionFactory
    private final List<ExtensionFactory> factories;

    // 当newInstance()时。会走到无参构造函数
    public AdaptiveExtensionFactory() {
        // 使用 ExtensionLoader 加载 ExtensionFactory 拓展对象的实现类
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<>();
        for (String name : loader.getSupportedExtensions()) {
            //扩展对象加入到集合中
            list.add(loader.getExtension(name));
        }
        factories = Collections.unmodifiableList(list);
    }

    @Override
    public <T> T getExtension(Class<T> type, String name) {
        // 遍历所有工厂进行查找
        for (ExtensionFactory factory : factories) {
            //通过扩展接口和扩展名获得扩展对象
            T extension = factory.getExtension(type, name);
            if (extension != null) {
                return extension;
            }
        }
        return null;
    }

}
