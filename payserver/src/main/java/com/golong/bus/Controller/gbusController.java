package com.golong.bus.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.golong.bus.lib.HttpClient;
import com.golong.bus.model.unionbank.backJson;
import net.sf.json.JSONObject;

@RestController
@RequestMapping(value="/pay",method= {RequestMethod.GET,RequestMethod.POST})
public class gbusController {

	    private final Logger logger = LoggerFactory.getLogger(this.getClass());
	    private final String md5Key = "123456789ABCDEFGHIJKLMN987654321";
 
		/*
		 * 银商取token
		 * 测试地址：http://210.22.91.77:29015/v1/token/access 
		 * 生产地址：https://api-mop.chinaums.com/v1/token/access
		 */
		@GetMapping("/GetAccessToken")
		public String GetAccessToken(String key) {
			String url="http://210.22.91.77:29015/v1/token/access";
		    JSONObject json = new JSONObject();
		    json.put("appid", "f0ec96ad2c3848b5b810e7aadf369e2f");
		    json.put("timestamp", WXPayUtil.getCurrentTimestamp());
		    json.put("nonce", WXPayUtil.generateNonceStr());
			String sign=WXPayUtil.Sha1(json.get("appid").toString()+json.get("timestamp").toString()+json.get("nonce").toString()+md5Key);//SignUtil.makeSign(json, md5Key);
            json.put("signature", sign);
			String spostjson=json.toString();
			String resJson=HttpClient.doPostStr(url, spostjson, "application/json;charset=UTF-8");
			backJson bjson=JSON.parseObject(resJson, backJson.class);
			return bjson.getAccessToken();
		}
}
