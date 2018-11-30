package yinq.Request;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by YinQ on 2018/11/29.
 */

public interface HttpRequestObservice {


    public void onRequestSuccess(Object result);
    public void onRequestFail(int errCode, String errMsg);

    public void onRequestComplete(int errCode) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}

