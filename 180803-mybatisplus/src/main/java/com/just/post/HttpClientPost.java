package com.just.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Test;

public class HttpClientPost {
	@Test
	public void form() throws Exception {
		String url = "http://localhost:8070/cart/save";
		for (int i = 0; i < 30; i++) {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("userId", "5");
			paramMap.put("itemId", ""+i);
			paramMap.put("itemTitle", "某商品 超值 物美价廉");
			paramMap.put("itemPrice", "100");
			paramMap.put("itemImage", "image");
			paramMap.put("num", "1");
			String result = httpPostWithForm(url, paramMap);
			System.out.println(result);
		}
	}

	public static String httpPostWithForm(String url, Map<String, String> paramMap) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			// String key = entry.getKey();
			// String value = entry.getValue();
			// BasicNameValuePair pair = new BasicNameValuePair(key, value);
			// pairList.add(pair);
			pairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "utf-8");
		}
		return respContent;
	}

	@Test
	public void json() throws Exception {
		String url = "http://localhost:8070/cart/query/5?page=1&row1";
		String result = httpPostWithJSON(url);
		System.out.println(result);
	}

	public static String httpPostWithJSON(String url) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient client = HttpClients.createDefault();
		String respContent = null;
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("name", "admin");
		jsonParam.put("pass", "123456");
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8"); // 解决中文乱码问题
		entity.setContentEncoding("utf-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);

		HttpResponse resp = client.execute(httpPost);
		if (resp.getStatusLine().getStatusCode() == 200) {
			HttpEntity he = resp.getEntity();
			respContent = EntityUtils.toString(he, "UTF-8");
		}
		return respContent;
	}
}
