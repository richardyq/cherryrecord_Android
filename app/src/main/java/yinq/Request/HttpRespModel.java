package yinq.Request;

import yinq.datamodel.JsonObject;

/**
 * Created by YinQ on 2018/11/29.
 */

public class HttpRespModel extends JsonObject {

    private int code;
    private String message;
    private Object result;

    public HttpRespModel(){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
