package demo.demo1.thrift;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
public class TestZookeeperClient {

    //private static final Logger log = LoggerFactory.getLogger(TestZookeeperClient.class);
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookeeperClient client = new ZookeeperClient();
        String host = "127.0.0.1:2181";

        // 连接zookeeper
        client.connectZookeeper(host);
        System.out.println("zookeeper连接成功！");

        // 创建节点
        byte[] data = { 1, 2, 3, 4, 5 };
        String result = client.createNode("/test4", data);
        System.out.println(result + "节点创建成功！");

        // 获取某路径下所有节点
        List<String> children = client.getChildren("/");
        for (String child : children) {
            System.out.println(child);
        }
        System.out.println("成功获取child节点");

        // 获取节点数据
        byte[] nodeData = client.getData("/test4");
        System.out.println(Arrays.toString(nodeData));
        System.out.println("成功获取节点数据！");

        // 更新节点数据
       /* data = "test data".getBytes();
        client.setData("/test3", data, 0);
        System.out.println("成功更新节点数据！");
        nodeData = client.getData("/test3");*/
        client.closeConnect();
    }

}
