package com.kest.ediscover.network;

import com.kest.ediscover.utils.DebugLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by dk on 2017/12/18.
 */

public class MessageDispatcher {
    public static final String BASE_API_URI = "http://pay.allbuy.me/";




    public static String JsonPost(final String path, Map<String, Object> params) {
        Map<String, Object> request = params;

        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(params.entrySet());

        //排序方法
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        StringBuilder s1 = new StringBuilder();
        //排序后
        for(Map.Entry<String, Object> m : infoIds){
            s1.append(m.getKey()).append("=").append(params.get(m.getKey())).append("&");
        }
        s1.append("os=androidApp&signcert=875878748as3dNLJK32fbn2vmnvmnhhj8");
        DebugLog.i(s1.toString());
        request.put("signature", getMD5(s1.toString()));
        request.put("os", "androidApp");
        //DebugLog.i("密文："+getMD5(s1.toString()));


        BufferedReader in = null;
        String result = "";
        OutputStream os = null;
        try {
            URL url = new URL(path);

            String content = String.valueOf(getjsonData(request));
            DebugLog.i("输入参数："+content);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
            os = conn.getOutputStream();
            os.write(content.getBytes());
            os.flush();

            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            DebugLog.i("网络请求状态码："+conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
        } catch (SocketTimeoutException e) {

            e.printStackTrace();
            return "错误";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "错误";
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "错误";
        } catch (IOException e) {
            e.printStackTrace();
            return "错误";
        }// 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static JSONObject getjsonData(Map<String, Object> params) {
        JSONObject js=new JSONObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                js.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return js;
    }

    public static String sendPost(String path, Map<String, Object> params) throws IOException {
        DebugLog.i("开始请求-------------------");
        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--";
        String ENDLINE = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        URL url = new URL(path);
        DebugLog.i("请求的地址：" + path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(10 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);


        Map<String, Object> request = params;

        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(params.entrySet());

        //排序方法
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        StringBuilder s1 = new StringBuilder();
        //排序后
        for(Map.Entry<String, Object> m : infoIds){
            s1.append(m.getKey()).append("=").append(params.get(m.getKey())).append("&");
        }
        s1.append("os=androidApp&signcert=875878748as3dnljk32fbn2vmnvmnhhj8");
        DebugLog.i(s1.toString());
        request.put("signature", getMD5(s1.toString()));
        request.put("os", "androidApp");
        DebugLog.i("密文："+getMD5(s1.toString()));

        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, Object> entry : request.entrySet()) {
                DebugLog.i(entry.getKey() + "=" + entry.getValue());
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(ENDLINE);
                sb.append("Content-Disposition: form-data; name=\""
                        + entry.getKey() + "\"" + ENDLINE);
                sb.append("Content-Type: text/plain; charset=" + CHARSET
                        + ENDLINE);
                sb.append("Content-Transfer-Encoding: 8bit" + ENDLINE);
                sb.append(ENDLINE);
                sb.append(entry.getValue());
                sb.append(ENDLINE);
            }
        }

        DataOutputStream outStream = new DataOutputStream(
                conn.getOutputStream());
        outStream.write(sb.toString().getBytes());

        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + ENDLINE).getBytes();
        outStream.write(end_data);
        outStream.flush();

        int status = conn.getResponseCode();
        DebugLog.i("返回的状态码：" + status);
        if (status != 200) {
            //DebugLog.e("返回的状态码：" + status);
        }

        InputStream in = conn.getInputStream();
        StringBuilder sb2 = new StringBuilder();
        if (status == 200) {
            int ch;
            while ((ch = in.read()) != -1) {
                sb2.append((char) ch);
            }
        }
        outStream.close();
        conn.disconnect();
        DebugLog.i("返回：" + sb2.toString());
        return sb2.toString();
    }

/*
    //生成MD5
    public static String getMD5(String message) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            byte[] messageByte = message.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
            md5 = bytesToHex(md5Byte);                            // 转换为16进制字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5;
    }*/
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            String md5=new BigInteger(1, md.digest()).toString(16);
            //BigInteger会把0省略掉，需补全至32位
            return fillMD5(md5);
        } catch (Exception e) {
            throw new RuntimeException("MD5加密错误:"+e.getMessage(),e);
        }
    }

    public static String fillMD5(String md5){
        return md5.length()==32?md5:fillMD5("0"+md5);
    }

    // 二进制转十六进制
    public static String bytesToHex(byte[] bytes) {
        StringBuffer hexStr = new StringBuffer();
        int num;
        for (int i = 0; i < bytes.length; i++) {
            num = bytes[i];
            if(num < 0) {
                num += 256;
            }
            if(num < 16){
                hexStr.append("0");
            }
            hexStr.append(Integer.toHexString(num));
        }
        return hexStr.toString().toUpperCase();
    }
}


