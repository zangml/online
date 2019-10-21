namespace java com.koala.learn.rpc.common

struct RPCResponse {
    1:  required i32 code;
    2:  required string msg;
    3:  required string data;
}