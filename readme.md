# 手写rpc框架

## 基于HttpClient
1. 客户端模拟浏览器发送请求，获取服务端的响应
2. 客户端解析响应，获取方法返回值 (json字符串形式)

## 基于RMI (Remote Method Invocation)
1. jdk1.2之后提供，不需要额外依赖
2. 远程接口需要继承java.rmi.Remote接口
3. 支持远程调用的方法均需要抛出RemoteException
4. 支持远程调用的实现类需要直接或者间接地实现Remote接口
5. 实现类可以继承UnicastRemoteObject以拥有远程调用的默认实现
6. 服务端实现服务接口，并在rmi注册中心中注册自身地址
7. 客户端根据远程服务的rmi地址获取对应的代理对象，实现远程调用

## 基于Zookeeper和RMI
1. Zookeeper作为注册中心
2. 服务端向zk中注册远程方法的rmi地址
3. 客户端从zk中获取远程方法的rmi地址，实现远程调用