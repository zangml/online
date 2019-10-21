include "common.thrift"
namespace java com.koala.learn.rpc

struct SqlInfo{
    1: required string sql
    2: required string answer
    3: required string data
}

service SqlProxyService {
   common.RPCResponse insertSql(1:SqlInfo sqlInfo)

   common.RPCResponse deleteSql(1:SqlInfo sqlInfo)

   common.RPCResponse querySql(1:SqlInfo sqlInfo)

   common.RPCResponse updateSql(1:SqlInfo sqlInfo)
}
