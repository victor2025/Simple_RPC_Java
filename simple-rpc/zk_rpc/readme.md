# 基于Zookeeper和RMI的rpc框架
## 核心思想
### 服务端
1. 服务端启动时，需要提供默认的服务端口号
2. 服务端启动时，需要制定Zookeeper所在的位置
3. 服务端启动时，提供要发布的服务对象(Remote接口的实现)
4. 服务端启动时，需要使用固定的逻辑拼接RMI的URI地址
### 客户端
1. 客户端启动时，需要制定Zookeeper所在的位置
2. 客户端启动时，需要提供创建代理类的接口类型
3. 客户端自动链接zk，并按照规则拼接节点名称
4. 节点中保存的数据就是远程服务的RMI地址，不需要记忆地址