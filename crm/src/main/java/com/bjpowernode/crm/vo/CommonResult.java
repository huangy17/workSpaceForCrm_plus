package com.bjpowernode.crm.vo;

/**
 * @author huangyb
 * @create 2021-08-07 16:38
 */
public class CommonResult {
    //private Integer code;
    private String msg;
    private Object objData;

    public CommonResult(Object data){
        this.objData = data;
    }

    public CommonResult(String msg, Object data) {
        //this.code = code;
        this.msg = msg;
        this.objData = data;
    }

   /* public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }*/

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObjData() {
        return objData;
    }

    public void setObjData(Object data) {
        this.objData = data;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                //"code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + objData +
                '}';
    }
}
