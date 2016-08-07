package demo.demo1.test;

import demo.demo1.thrift.ThriftService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by user on 2016/8/1.
 */
public class Test {
    public static void main(String[] args) {
        try {


            /**
             * 客户端编码基本步骤：
             * 创建Transport
             创建TProtocol
             基于TTransport和TProtocol创建 Client
             调用Client的相应方法
             */
            TTransport transport = new TSocket("localhost", 7911);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            ThriftService.Client client = new ThriftService.Client(protocol);
            System.out.println(client.add(77, 5));
            transport.close();

        } catch (TTransportException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
