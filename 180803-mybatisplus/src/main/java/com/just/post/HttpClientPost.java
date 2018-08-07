package com.just.post;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientPost {
	@Test
	public void form() throws Exception {
		String url = "http://localhost:8070/cart/save";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("user_id", "5");
		paramMap.put("item_id", "2");
		paramMap.put("item_title", "某商品 超值 物美价廉");
		paramMap.put("item_price", "100");
		paramMap.put("item_image", "image");
		paramMap.put("num", "1");
		String result = httpPostWithForm(url, paramMap);
		System.out.println(result);

	}

	public static String httpPostWithForm(String url, Map<String, String> paramMap) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			BasicNameValuePair pair = new BasicNameValuePair(key, value);
			pairList.add(pair);
		}
		httpPost.setEntity(new UrlEncodedFormEntity(pairList, "urf-8"));
		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "utf-8");
		}
		return respContent;
	}
}
