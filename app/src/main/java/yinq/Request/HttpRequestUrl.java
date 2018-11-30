package yinq.Request;

/**
 * Created by YinQ on 2018/11/29.
 */

public class HttpRequestUrl {
    static String baseUrl = "http://148.70.21.73:8080/CherryDialyRecord";

    static public String getHttpUrl(String service, String method){
        String httpUrl = baseUrl + "/base.do?";
        httpUrl += ("&service=" + service);
        httpUrl += ("&method=" + method);
        return httpUrl;
    }
}
