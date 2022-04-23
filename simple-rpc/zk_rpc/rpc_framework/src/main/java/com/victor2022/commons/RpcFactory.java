package com.victor2022.commons;

import com.victor2022.commons.connection.ZkConnection;
import com.victor2022.commons.constants.CommonConst;
import com.victor2022.commons.registry.RpcRegistry;
import org.apache.commons.lang.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: victor2022
 * @date: 2022/4/23 下午2:08
 * @description: 框架入口
 */
public class RpcFactory {

    // 配置信息
    private static final Properties config = new Properties();
    // 服务配置信息
    private static final Properties serviceConfig = new Properties();
    // 连接对象
    private static final ZkConnection connection;
    // 注册器对象
    private static final RpcRegistry registry;

    /**
     * @param null:
     * @return: null
     * @author: victor2022
     * @date: 2022/4/23 下午2:08
     * @description: 初始化过程
     * 1. 提供配置文件，命名为rpc-properties.yml
     * 2. 配置文件结构
     *  - registry.ip 默认localhost
     *  - registry.port 默认9090
     *  - zk.server 默认localhost:2181
     *  - zk.sessionTimeout 默认10000
     *
     */
    static {
        try {
            // 获取配置文件输入流
            InputStream is = RpcFactory.class.getClassLoader()
                    .getResourceAsStream(CommonConst.PROPERTIES_FILENAME);
            // 读取配置文件，初始化配置对象

            config.load(is);
            // 获取指定配置信息
            // 注册中心
            // 获取注册中心地址
            String regIp = config.getProperty(CommonConst.REG_IP_SOURCE);
            if(StringUtils.isEmpty(regIp))regIp = CommonConst.REG_IP_DEFAULT;
            // 获取注册中心端口号
            String regPortStr = config.getProperty(CommonConst.REG_PORT_SOURCE);
            Integer regPort = StringUtils.isEmpty(regPortStr)?CommonConst.REG_PORT_DEFAULT:Integer.valueOf(regPortStr);
            // Zookeeper
            // zk地址
            String zkServer = config.getProperty(CommonConst.ZK_SERVER_SOURCE);
            if(StringUtils.isEmpty(zkServer))zkServer = CommonConst.ZK_SERVER_DEFAULT;
            // zk超时时间
            String zkTimeoutStr = config.getProperty(CommonConst.ZK_TIMEOUT_SOURCE);
            Integer zkTimeout = StringUtils.isEmpty(zkTimeoutStr)?CommonConst.ZK_TIMEOUT_DEFAULT:Integer.valueOf(zkTimeoutStr);

            // 创建zkConnection对象
            connection = new ZkConnection(zkServer,zkTimeout);
            System.out.println("Zookeeper连接对象创建完成");
            // 创建注册器对象
            registry = new RpcRegistry(regIp,regPort,connection);
            System.out.println("RMI注册中心创建完成");
            // 创建rmi注册器
            LocateRegistry.createRegistry(regPort);
            // 在zk中初始化rpc节点
            initRootNode();
            System.out.println("节点初始化完成");
            // 初始化注册对象
            initRegClasses();
            System.out.println("初始化服务注册完成");
        } catch (Exception e) {
            e.printStackTrace();
            // 若初始化异常，则抛出错误，中断虚拟机
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * @return: void
     * @author: victor2022
     * @date: 2022/4/23 下午4:16
     * @description: 根据配置文件注册对象
     */
    private static void initRegClasses(){
        try {
            InputStream is = RpcFactory.class.getClassLoader().getResourceAsStream(CommonConst.SERVICE_PROPERTIES_FILENAME);
            if(is!=null){
                // 若文件存在，则根据文件注册对应的对象
                serviceConfig.load(is);
                for (Map.Entry<Object, Object> entry : serviceConfig.entrySet()) {
                    // 获取接口和对象全类名
                    String interfaceName = (String) entry.getKey();
                    String className = (String) entry.getValue();
                    // 获取Class对象
                    Class<Remote> iClazz = (Class<Remote>) Class.forName(interfaceName);
                    Class<Remote> clazz = (Class<Remote>) Class.forName(className);
                    // 创建实现类的对象
                    Remote obj = clazz.newInstance();
                    // 注册当前对象
                    registerService(iClazz,obj);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (KeeperException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return: void
     * @author: victor2022
     * @date: 2022/4/23 下午3:21
     * @description: 创建指定根节点
     */
    private static void initRootNode(){
        String[] paths = CommonConst.ZK_ROOT.split("/");
        recursionCreatePath(paths,0,"/");
    }

    /**
     * @param paths:
     * @param ind:
     * @param prePath:
     * @return: void
     * @author: victor2022
     * @date: 2022/4/23 下午4:15
     * @description: 递归创建zk目录
     */
    private static void recursionCreatePath(String[] paths, int ind, String prePath){
        if(ind<paths.length){
            try {
                // 获取当前路径
                String currPath = paths[ind];
                // 判断是否创建
                if(currPath.length()!=0){
                    List<String> children = connection.getConnection().getChildren(prePath, false);
                    prePath = "/".equals(prePath)?prePath+currPath:prePath+"/"+currPath;
                    if(!children.contains(currPath)){
                        connection.getConnection().create(prePath,
                                null,ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    }
                }
                recursionCreatePath(paths,ind+1,prePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (KeeperException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @param serviceInterface:
     * @param serviceObject:
     * @return: void
     * @author: victor2022
     * @date: 2022/4/23 下午2:45
     * @description: 快速注册服务和创建客户端代理对象的工具方法
     */
    public static void registerService(Class<? extends Remote> serviceInterface, Remote serviceObject) throws IOException, InterruptedException, KeeperException {
        registry.registerService(serviceInterface,serviceObject);
    }

    /**
     * @param serviceInterface:
     * @return: T
     * @author: victor2022
     * @date: 2022/4/23 下午2:46
     * @description: 获取指定接口代理对象工具类
     */
    public static <T extends Remote> T getServiceProxy(Class<T> serviceInterface) throws NotBoundException, IOException, InterruptedException, KeeperException {
        return registry.getServiceProxy(serviceInterface);
    }
}
