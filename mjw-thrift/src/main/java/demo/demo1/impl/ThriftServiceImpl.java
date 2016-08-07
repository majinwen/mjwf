package demo.demo1.impl;

import demo.demo1.thrift.ThriftService;
import org.apache.thrift.TException;

/**
 * Created by user on 2016/8/1.
 */
public class ThriftServiceImpl implements ThriftService.Iface {

    @Override
    public int add(int a, int b) throws TException {
        return a + b;
    }
}
