package org.jeeplus.cmd;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.common.collect.Lists;

import net.sf.json.JSONObject;

public final class HttpTool {
	
	private final static CloseableHttpClient client = HttpClients.createDefault();
	private final static RequestConfig config = RequestConfig.custom().setConnectTimeout(5000)
			.setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
	public final static JSONObject post(final String url,final Map<String, String> params) {
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		post.setConfig(config);
		try {
			if(params!=null) {
				List<NameValuePair> list = Lists.newArrayList();
				params.forEach((k,v)->{
					list.add(new BasicNameValuePair(k, v));
				});
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list,"UTF-8");
				post.setEntity(formEntity);
			}
			HttpResponse response = client.execute(post);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			return JSONObject.fromObject(result);
		} catch (Exception e) {
			throw new RuntimeException("POST("+url+")请求失败");
		}finally {
			post.releaseConnection();
		}
	}
	
	public final static JSONObject get(final String url,final Map<String, String> params) {
		StringBuffer sb = new StringBuffer(url).append("?myparam=1");
		if(params!=null) {
			params.forEach((k,v)->{
				sb.append("&").append(k).append("=").append(v);
			});
		}
		HttpGet get = new HttpGet(sb.toString().replaceAll(" ", "%20"));
		get.setConfig(config);
		try {
			HttpResponse response = client.execute(get);
			String result = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println(result);
			return JSONObject.fromObject(result);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(url);
			throw new RuntimeException("GET("+url+")请求失败");
		}finally {
			get.releaseConnection();
		}
	}
}
