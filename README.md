# **RPC基础版**

### 大家好，我是程序员熊大

这个RPC项目是我自己捣鼓的一个项目，因为我之前学的用的都是Spring Cloud Alibaba + Restful风格的那一套基于HTTP请求方式，但是！但是！在我工作后，因为我们公司业务要用到RPC，所以我下班后开始内卷研究起来这个RPC，功夫不负有心人啊！耗时3周我终于是把RPC学会了（呜呜呜），在后来我转正后参与项目开发，我们老大当时还以为我不会，让我找其他同事协助，我当时就把我对RPC的理解和场景叽里呱啦的和他说了一遍，哈哈哈，那个B装的，爽！！当然啦，当时了解到的也只是片面一些，不过经过慢慢的磨练，我算是用习惯了，这个项目的实现思路是，我会先让AI给我梳理项目的流程和问题，然后逐一击破（当然过程的报错让人很痛苦）。

项目有基础版和进阶版

基础版：https://github.com/tjt987666/RPC.git

进阶版: https://github.com/tjt987666/RPC_Max.git



## 什么是 RPC?

RPC全称叫Remote Procedure Call即远程过程调用，是一种计算机通信协议，它允许程序在不同的计算机之间进行通信和交互，就像本地调用一样。

简单来说就是我开了一家饭店，现在你作为消费者你想把这个饭买回家，如果是以前你要下楼然后来我的饭店里面去买饭，费时费力。但是现在有了手机网络外卖平台美团等，你只需要在家里动动手指，就能把这个外卖让骑手把这个饭配送到你家去，你不需要去关注这个网络传输是怎么传输，平台这么分发订单的，外卖这么配送过来的，你只需要付完款之后等待外卖送来享用就好了。RPC也是同理，我们就可以理解为我消费者不需要管他中间这个服务是怎么执行的，流程有哪些，这些都不需要我们来管，我们只负责得到响应的结果。

## 为什么需要 RPC?

回到RPC的概念，RPC允许一个程序（称为服务消费者）像调用自己程序的方法一样，调用另一个程序（称为服务提供者）的接口，而不需要了解数据的传输处理过程、底层网络通信的细节等。这些都会由RPC框架帮你完成，使得开发者可以轻松调用远程服务，快速开发分布式系统。

举个例子，现在有个项目A提供了点餐服务，项目B需要调用点餐服务完成下单。点餐服务和接口的示例伪代码如下：

```markdown
java
interface OrderService {
// 点餐，返回 orderId
long order(参数1, 参数2, 参数3);
}
```
如果没有RPC框架，项目B怎么调用项目A的服务呢？

首先，由于项目A和项目B都是独立的系统，不能像SDK一样作为依赖包引入。那么就需要项目A提供web服务，并且编写一个点餐接口暴露服务，比如访问 `http://localhost:8080/xiongda` 就能调用点餐服务；然后项目B作为服务消费者，需要自己构造请求，并通过HttpClient请求上述地址。如果项目B需要调用更多第三方服务，每个服务和方法的调用都编写一个HTTP请求，那么会非常麻烦！

示例伪代码如下：

```
java
url = "http://localhost:8080/xiongda";
req = new Req(参数1, 参数2, 参数3);
res = httpClient.post(url).body(req).execute();
orderId = res.data.orderId;
```
而有了RPC框架，项目B可以通过一行代码完成调用！

示例伪代码如下：

```
java
orderId = orderService.order(参数1, 参数2, 参数3);
```
是不是简单多了，直接丝滑起飞。

## 简易版的RPC实现思路

### 架构图
![rpc架构](https://github.com/user-attachments/assets/ac693dde-47c5-4353-a333-559d2dad5562)



### 具体实现

#### 第一阶段（具体流程）

1. **创建模块**
![结构](https://github.com/user-attachments/assets/e17ffeb5-9f9c-4779-8719-cc3901790285)



   - `example-common`: 示例代码的公共模块，包括接口和Model
   - `example-consumer`: 服务的消费者
   - `example-provider`: 服务的提供者
   - `xiongda-rpc-easy`: 简易版的RPC框架

2. **在公共模块编写服务和相关接口**（`common/UserService`）

3. **服务提供者实现公共类的服务**（`provider/UserServiceImpl`）

4. **服务调用者发起调用**（`consumer/EasyConsumerExample`）

#### 第二阶段（具体实现）

1. **接入高性能的NIO框架Vert.x作为RPC框架的服务器**

2. **创建本地服务注册器LocalRegistry并使用线程安全的ConcurrentHashMap储存服务注册信息**

3. **服务提供者注册服务**

4. **编写JAVA原生序列化器（序列化/反序列化）**

5. **提供者处理调用（请求处理器）**

6. **消费方发起调用（代理）**

7. **静态代理（UserServiceProxy）**

8. **JDK动态代理（ServiceProxyFactory）**

9. **发起调用**

