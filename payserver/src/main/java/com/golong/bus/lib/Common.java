package com.golong.bus.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class Common {
	
    private static final String APPLICATION_JSON = "application/json";
    
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static String httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
        String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

        StringEntity se = new StringEntity(encoderJson);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpPost.setEntity(se);
        HttpResponse httpResponse =httpClient.execute(httpPost);
     // 请求结果
        String responeMsg = "";
        if (httpResponse.getStatusLine().getStatusCode() == 200)
        {
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null)
        {
        responeMsg = EntityUtils.toString(httpEntity);// 取出应答字符串
        responeMsg = URLDecoder.decode(responeMsg, "UTF-8");// 转码翻译操作
        }
        }
        return responeMsg;
    }

	public static String readLogInfo(String filePath) {

        StringBuffer sb = new StringBuffer("");
        // 创建文件输入流对象
        Reader reader = null;
        //
        InputStreamReader inputStreamReader = null;
        // 创建文件输入流缓存对象
        BufferedReader bufferedReader = null;
        try {
            File file = new File(filePath);
            FileUtils.touch(file);
             
            reader = new FileReader(file);
            inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
             
            String lineString = bufferedReader.readLine();
            while (lineString != null) {
                sb.append(lineString);
                lineString = bufferedReader.readLine();
            }
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if(inputStreamReader!=null){
                    inputStreamReader.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
	public static void writeLog(String filePath, String content) {  
        String str = new String(); //原有txt内容  
        String s1 = new String();//内容更新  
        try {  
            File f = new File(filePath);  
            if (f.exists()) {  
                System.out.print("文件存在");  
            } else {  
                System.out.print("文件不存在");  
                f.createNewFile();// 不存在则创建  
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
  
            while ((str = input.readLine()) != null) {  
                s1 += str + "\n";  
            }  
            System.out.println(s1);  
            input.close();  
            s1 += content;  
  
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    }  
  
}
