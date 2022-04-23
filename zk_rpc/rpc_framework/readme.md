# rpc框架
## 程序结构
1. RpcFactory:程序入口
2. registry: 注册工具
3. connection: 连接工具
4. constants: 常量

## 配置文件结构
### rpc.properties
1. registry.ip 默认localhost
2. registry.port 默认9090 
3. zk.server 默认localhost:2181 
4. zk.sessionTimeout 默认10000
### rpc-service.properties
1. 配置初始化时需要注册的对象
2. key为接口全类名
3. value为实现类的全类名

## 异常记录
1. 根节点不存在异常 
   * 解决: 在初始化时，判断根节点是否存在，若不存在，则递归创建
2. 反复注册，会抛出异常
   * 解决: 注册某个节点之前，先判断当前节点是否已经存在，若存在，则先删除该节点