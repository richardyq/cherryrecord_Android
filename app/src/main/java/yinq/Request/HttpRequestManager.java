package yinq.Request;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import android.os.Handler;
import android.os.Message;

/**
 * Created by YinQ on 2018/11/29.
 */

public class HttpRequestManager {
    public static class RequestManagerParam {
        private Class requestClass;
        private String json;
        private HttpRequestObservice observice;

        public RequestManagerParam(Class requestClass, String json, HttpRequestObservice observice) {
            this.requestClass = requestClass;
            this.json = json;
            this.observice = observice;
        }


        public Class getRequestClass() {
            return requestClass;
        }

        public void setRequestClass(Class requestClass) {
            this.requestClass = requestClass;
        }

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }

        public HttpRequestObservice getObservice() {
            return observice;
        }

        public void setObservice(HttpRequestObservice observice) {
            this.observice = observice;
        }
    }

    static HttpRequestManager defaultManager;
    private static final int createRequestMsgId = 0x1000;

    private Handler managerHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == createRequestMsgId){
                RequestManagerParam managerParam = (RequestManagerParam) msg.obj;
                Class requestClass = managerParam.getRequestClass();
                String json = managerParam.getJson();
                HttpRequestObservice observice = managerParam.getObservice();

                try {
                    createHttpRequest(requestClass, json, observice);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    public static HttpRequestManager getDefaultManager(){
        if (defaultManager == null){
            defaultManager = new HttpRequestManager();

        }
        return defaultManager;
    }

    static public void createRequest(Class requestClass, String json, HttpRequestObservice observice) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        HttpRequestManager manager = getDefaultManager();
        manager.maincreateHttpRequest(requestClass, json, observice);

    }

    public void maincreateHttpRequest(Class cls, String json, HttpRequestObservice observice){
        Message message = new Message();
        message.what = createRequestMsgId;
        RequestManagerParam param = new RequestManagerParam(cls, json, observice);
        message.obj = param;
        managerHandler.sendMessage(message);
    }

    public HttpRequest createHttpRequest(Class cls, String json, HttpRequestObservice observice) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        System.out.println("createHttpRequest entry...");
        Constructor constructor = cls.getConstructor(String.class, HttpRequestObservice.class);
        HttpRequest request = (HttpRequest) constructor.newInstance(json, observice);
        System.out.println("createHttpRequest request " + request.getClass().getName());

        request.start();
        return request;
    }

}
