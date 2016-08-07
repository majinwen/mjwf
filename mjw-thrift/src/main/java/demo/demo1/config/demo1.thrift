namespace java com.mf.thrift.demo1  // defines the namespace

typedef i32 int  //typedefs to get convenient names for your types

service ThriftService {
    int add(1:int a,2:int b),
}