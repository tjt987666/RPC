package com.zking.xiongda;

import com.zking.xiongda.regisrty.LocalRegistry;
import com.zking.xiongda.serializer.UserService;
import com.zking.xiongda.serializer.UserServiceImpl;
import com.zking.xiongda.service.HttpServer;
import com.zking.xiongda.service.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {

    public static void main(String[] args) {
        // 注入服务 获取到接口名字和对应的实现类
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);


        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}

