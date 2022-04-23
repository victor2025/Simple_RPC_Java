package com.victor2022.commons.registry;

import com.victor2022.commons.connection.ZkConnection;
import com.victor2022.commons.constants.CommonConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.util.List;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午1:42
 * @description: 注册器工具
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcRegistry {

    private String ip;
    private Integer port;
    private ZkConnection connection;
    /**
     * @param serviceInterface: Remote的子接口
     * @param serviceObject: 实现serviceInterface, 直接或间接实现Remote
     * @return: void
     * @author: victor2022
     * @date: 2022/4/23 下午1:45
     * @throws: 抛出异常代表注册失败
     * @description: 注册服务方法
     * 1. 拼接RMI访问地址URI
     * 2. 将URI储存在zk中
     */
    public void registerService(Class<? extends Remote> serviceInterface, Remote serviceObject) throws IOException, InterruptedException, KeeperException {
        StringBuilder sb = new StringBuilder();
        // 拼接ip和端口
        sb.append(CommonConst.RMI_START).append(this.ip).append(":").append(this.port).append("/");
        // 拼接接口名称
        sb.append(serviceInterface.getName());
        // 生成rmi
        String rmi = sb.toString();
        // 按照规则拼接一个zk存储节点
        String path = CommonConst.ZK_ROOT+"/"+serviceInterface.getName();
        // 判断当前节点是否已经存在
        List<String> children = connection.getConnection().getChildren(CommonConst.ZK_ROOT, false);
        if(children.contains(serviceInterface.getName())){
            // 节点存在，需要先删除
            Stat stat = new Stat();
            connection.getConnection().getData(path,false,stat);
            connection.getConnection().delete(path,stat.getCversion());
        }
        // 将uri存储到zk中
        connection.getConnection().create(path,
                rmi.getBytes(StandardCharsets.UTF_8),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        // 注册rmi服务
        Naming.rebind(rmi,serviceObject);
        System.out.println("注册了"+serviceInterface.getName());
    }

    /**
     * @return: java.lang.Object
     * @author: victor2022
     * @date: 2022/4/23 下午2:02
     * @description: 根据服务接口类型，访问zk，获取RMI的远程代理对象
     * 1. 拼接zk中节点名称
     * 2. 访问zk，查询节点中的数据
     * 3. 根据查询结果，创建代理对象
     */
    public <T extends Remote> T getServiceProxy(Class<T> serviceInterface) throws IOException, InterruptedException, KeeperException, NotBoundException {
        String path = CommonConst.ZK_ROOT+"/"+serviceInterface.getName();
        byte[] data = connection.getConnection().getData(path, false, null);
        // 根据字节数组生成RMI的访问地址
        String rmi = new String(data);
        // 创建代理对象
        Object obj = Naming.lookup(rmi);
        return (T) obj;
    }
}
