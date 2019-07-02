package org.jeeplus.cmd.photo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeeplus.cmd.HttpTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 照片命令
 * @author Jin
 *
 */
public final class PhotoCmd {
	/**
	 * 人脸照片相似性比对 
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param img1  图片1 base64
	 * @param img2 图片2 base64
	 * @return null 不识别或不成功  不为null：相似度
	 */
	public final static Double photoComparison(String host,String pass,String img1,String img2) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/face/photoComparison");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("img1", img1);
		params.put("img2", img2);
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			if(json.containsKey("data")) {
				return json.getDouble("data");
			}else {
				throw new RuntimeException(json.getString("msg"));
			}
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	
	/**
	 * 清空人员注册照片
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param personId 人员id
	 * @return 
	 */
	public final static boolean deletePerson(String host,String pass,String personId) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/face/deletePerson");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("personId", personId);
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			return true;
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	/**
	 * 图片删除
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param faceId 图片id
	 * @return 
	 */
	public final static boolean delete(String host,String pass,String faceId) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/face/delete");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("faceId", faceId);
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			return true;
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	/**
	 * 图片更新
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param personId 人员id
	 * @param faceId 图片id
	 * @param imgBase64 图片base64
	 * @return 
	 */
	public final static boolean update(String host,String pass,String personId,String faceId,String imgBase64) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/face/update");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("personId", personId);
		params.put("faceId", faceId);
		params.put("imgBase64", imgBase64);
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			return true;
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	/**
	 * 特征注册（和图片注册类似， 比图片注册快）
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param personId 人员id
	 * @param feature 特征码
	 * @param featureKey 特征密钥
	 * @return
	 */
	public final static SearchPhoto featureReg(String host,String pass,String personId,
			String feature,String featureKey) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/face/featureReg");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("personId", personId);
		params.put("faceId", "");
		params.put("feature", feature);
		params.put("featureKey", featureKey);
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")){
			return (SearchPhoto) JSONObject.toBean(json.getJSONObject("data"),SearchPhoto.class);
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	/**
	 * 图片查询
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param personId 人员id
	 * @return {@link org.jeeplus.cmd.photo.SearchPhoto}
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public final static List<SearchPhoto> find(String host,String pass,String personId) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/face/find");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("personId", personId);
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")){
			return JSONArray.toList(json.getJSONArray("data"), SearchPhoto.class);
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	/**
	 * 拍照注册
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param personId 人员id
	 * @return 通过personid查询图片id
	 */
	public final static boolean takeImg(String host,String pass,String personId) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/face/takeImg");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("personId", personId);
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			return true;
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	/**
	 * 图片注册
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param personId 人员id
	 * @param faceId 图片id（为空，系统自动生成）
	 * @param imgBase64 图片base64
	 * @return 注册成功返回faceId 不成功返回null
	 */
	public final static String create(String host,String pass,String personId,String faceId,String imgBase64) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/face/create");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("personId", personId);
		params.put("faceId", faceId);
		params.put("imgBase64", imgBase64);
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			if(!json.containsKey("data")) {
				return "";
			}
			return json.getString("data");
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
}
