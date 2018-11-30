package yinq.Request;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.io.UnsupportedEncodingException;

import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by YinQ on 2018/11/29.
 */

public class HttpRequest extends Thread {

    private String httpUrl;
    private String paramJson;
    private HttpRequestObservice respObservice;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (respCodeIsSuccess(msg.what) == true){
                if (respObservice != null){
                    respObservice.onRequestSuccess(msg.obj);

                }
                else {
                    respObservice.onRequestFail(msg.what, (String)msg.obj);
                }

                try {
                    respObservice.onRequestComplete(msg.what);
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

    public HttpRequest(){

    }

    public HttpRequest( String param, HttpRequestObservice observice) {
        initWith(param, observice);
    }

    public void initWith(String param, HttpRequestObservice observice){
        httpUrl = getHttpUrl();
        paramJson = param;
        respObservice = observice;
    }

    protected String getHttpUrl(){
        return null;
    }

    @Override
    public void run() {
        try {

            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(httpUrl).openConnection();
            //设置请求方式,请求超时信息
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            //设置运行输入,输出:
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Content-type", "application/json; charset=UTF-8");

            // 设定请求的方法为"POST"，默认是GET
            httpURLConnection.setRequestMethod("POST");

            //Post方式不能缓存,需手动设置为false
            httpURLConnection.setUseCaches(false);

            if (paramJson != null && !paramJson.isEmpty()) {
                DataOutputStream out = new DataOutputStream(httpURLConnection.getOutputStream());
                out.writeBytes(paramJson);
                out.flush();
                out.close();
            }

            // 读取响应
            int statusCode = httpURLConnection.getResponseCode();

            if (statusCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String lines;
                StringBuffer sb = new StringBuffer("");
                while ((lines = reader.readLine()) != null) {
                    lines = URLDecoder.decode(lines, "utf-8");
                    sb.append(lines);
                }
                System.out.println(sb);

                HttpRespModel resp = (HttpRespModel) new HttpRespModel().fromJson(sb.toString());

                reader.close();

                if (respCodeIsSuccess(resp.getCode())) {
                    //请求成功
                    Object result = getRespResult(resp.getResult());

                    Message message = new Message();
                    message.what = resp.getCode();
                    message.obj = result;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = resp.getCode();
                    message.obj = resp.getMessage();
                    handler.sendMessage(message);
                }
            } else {
                httpRequestFailed();
            }

            // 断开连接
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {

            e.printStackTrace();
            httpRequestFailed();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            httpRequestFailed();
        } catch (IOException e) {
            e.printStackTrace();
            httpRequestFailed();
        }
    }

    private void httpRequestFailed(){
        HttpRespModel resp = new HttpRespModel();
        resp.setCode(-1);
        resp.setMessage("服务器请求数据失败。");
        Message message = new Message();
        message.what=resp.getCode();
        message.obj = resp.getMessage();
        handler.sendMessage(message);
    }

    protected boolean respCodeIsSuccess(int code){
        return code == 0;
    }

    protected Object getRespResult(Object respResult){
        return respResult;
    }

}
