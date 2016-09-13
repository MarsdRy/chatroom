package cn.chatweb.bean.base;

/**
 * Created by 哲 on 2016/9/9.
 */
public class AjaxResult {

    private String message;
    private int errorCode = 0; // 0: normal , >=1 : error
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
