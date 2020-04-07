package com.sunhao.secspike.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SunYaHao
 * @version 1.0
 * @date 2020/3/29  14:55
 */
@Getter
@Setter
public class CodeMsg {
    private int code;
    private String msg;

    private CodeMsg(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg ACCESS_LIMIT_REACHED= new CodeMsg(500104, "访问高峰期，请稍等！");
    //登录模块 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg ACCOUNT_DISABLED = new CodeMsg(500212, "账号被禁用");
    public static CodeMsg ACCOUNT_NOT_EXIST = new CodeMsg(500214, "账号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");

    //商品模块 5003XX

    //订单模块 5004XX
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");

    //秒杀模块 5005XX
    public static CodeMsg SECSPIKE_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
    public static CodeMsg REPEATE_SECKILL = new CodeMsg(500501, "不能重复秒杀");
    public static CodeMsg SECSPIKE_WAIT = new CodeMsg(500502, "等待队列处理中");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500503, "请求非法");

    @Override
    public String toString(){
        return "CodeMsg：code=" + String.valueOf(code) +";msg="+msg;
    }
}
