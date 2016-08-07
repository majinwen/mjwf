package demo.demo1.soas;

import demo.demo1.impl.ThriftServiceImpl;
import demo.demo1.thrift.ThriftService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Created by user on 2016/8/1.
 */
public class ThriftServer {
    public static void main(String[] args) {
        try {

            /**
             * 服务端编码基本步骤：
             * 实现服务处理接口impl
             创建TProcessor
             创建TServerTransport
             创建TProtocol
             创建TServer
             启动Server
             */
            TServerSocket serverTransport = new TServerSocket(7911);

            TBinaryProtocol.Factory proFactory = new TBinaryProtocol.Factory();

            TProcessor processor = new ThriftService.Processor<ThriftService.Iface>(
                    new ThriftServiceImpl());
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(processor);
            tArgs.protocolFactory(proFactory);

            TServer server = new TSimpleServer(tArgs);
            System.out.println("Start server on port 7911....");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

}
