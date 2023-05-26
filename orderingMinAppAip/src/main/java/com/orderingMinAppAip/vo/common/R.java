package com.orderingMinAppAip.vo.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {

    private Integer code;
    private  T data;
    private String message;

    public R() {
        super();
    }


    public static R  succeed(){
        R r = new R();
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }

    public static R  succeed(Object data){
        R r = new R();
        r.setCode(200);
        r.setData(data);
        r.setMessage("成功");
        return r;
    }

    public static R  succeed(Integer code,String message){
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }


    public  static R  failed(Integer code,String message){
        R r = new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }
}
