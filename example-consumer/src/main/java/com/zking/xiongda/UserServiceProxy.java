package com.zking.xiongda;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.zking.xiongda.model.RpcRequest;
import com.zking.xiongda.model.RpcResponse;
import com.zking.xiongda.model.User;
import com.zking.xiongda.serializer.JdkSerializer;
import com.zking.xiongda.serializer.Serializer;
import com.zking.xiongda.serializer.UserService;

import java.io.IOException;

/**
 * 静态代理类，用于调用远程服务
 */
public class UserServiceProxy implements UserService {

    @Override
    public User getUser(User user) {
        // 指定序列化器，这里使用的是 JDK 序列化器
        Serializer serializer = new JdkSerializer();

        // 创建 RPC 请求对象
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName()) // 设置服务名称
                .methodName("getUser") // 设置方法名称
                .parameterTypes(new Class[]{User.class}) // 设置参数类型
                .args(new Object[]{user}) // 设置参数值
                .build();

        try {
            // 将 RPC 请求对象序列化为字节数组
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 发送 HTTP POST 请求到指定的 URL
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes) // 设置请求体
                    .execute()) { // 执行请求
                result = httpResponse.bodyBytes(); // 获取响应体的字节数组
            }

            // 将响应体的字节数组反序列化为 RpcResponse 对象
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);

            // 返回 RpcResponse 中的数据，即 User 对象
            return (User) rpcResponse.getData();

        } catch (IOException e) {
            // 捕获并打印异常信息
            e.printStackTrace();
        }

        // 如果发生异常，返回 null
        return null;
    }
}
