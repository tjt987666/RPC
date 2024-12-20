package com.zking.xiongda;

import com.zking.xiongda.model.User;
import com.zking.xiongda.proxy.ServiceProxyFactory;
import com.zking.xiongda.serializer.UserService;

/**
 * 示例消费者类，演示如何使用 EasyConsumer 调用远程服务。
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 创建 UserService 的代理对象，用于远程调用
//        UserService userService = new UserServiceProxy();
// 动态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);


        // UserService userService = null; // 注释掉的代码，用于调试或备用

        // 创建一个 User 对象，并设置用户名
        User user = new User();
        user.setName("xiongda");
//
//        // 发起远程调用，获取用户信息
        User user1 = userService.getUser(user);
//
//        // 检查返回的用户对象是否为空
        if (user1 != null) {
            // 如果不为空，打印用户名称
            System.out.println("user1: " + user1.getName());
        } else {
            // 如果为空，打印提示信息
            System.out.println("user1 is null");
        }
    }
}
