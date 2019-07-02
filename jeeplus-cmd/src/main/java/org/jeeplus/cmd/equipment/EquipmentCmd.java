package org.jeeplus.cmd.equipment;

import java.util.HashMap;
import java.util.Map;

import org.jeeplus.cmd.HttpTool;

import net.sf.json.JSONObject;

/**
 * 设备管理命令
 * @author Jin
 */
public final class EquipmentCmd {
	
	/**
	 * 设备心跳回调 设备每隔一分钟会向该接口POST字段deviceKey、time、ip、personCount、faceCount和version
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param url 回调地址》 传入内容为空可以清空回调地址，清空后识别成功将不再进行回调
	 * @return
	 */
	public final static boolean setDeviceHeartBeat(String host,String pass,String url) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/setDeviceHeartBeat");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("url", url);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	
	/**
	 * 注册照片回调 
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param url 回调地址》 传入内容为空可以清空回调地址，清空后识别成功将不再进行回调
	 * @return
	 */
	public final static boolean setImgRegCallBack(String host,String pass,String url) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/setImgRegCallBack");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("url", url);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 识别回调<br>a)设备配置接口设置saveIdentifyTime（识别记录时间窗）后，在该时间间隔内，同一个人多次识别只会向回调地址POST一条识别记录
	 * <br>b）URL端口说明：若使用页面作为回调地址，端口号须为8090；若使用API作为回调地址，端口号最大为9999，且路由器对该端口号开放。
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param callbackUrl 识别回调POST请求URL，为空时，则为禁止回调。
	 * @return
	 */
	public final static boolean setIdentifyCallBack(String host,String pass,String callbackUrl) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/setIdentifyCallBack");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("callbackUrl", callbackUrl);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	
	/**
	 * 大屏背景图片配置
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param img 图片的base64编码字符串（图片分辨率必须小于1920×1080）
	 * @return
	 */
	public final static boolean setActScreenImg(String host,String pass,String img) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/device/setActScreenImg");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("img", img);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 大屏模式接口
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param screenMode 1:传统模式，文字内容编辑参见设备配置，Logo编辑参见修改Logo
	 * 					 2:定义大屏背景模式，更换背景图片
	 * @return
	 */
	public final static boolean setScreenMode(String host,String pass,Integer screenMode) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/device/setScreenMode");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("screenMode", screenMode.toString());
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 设备开门
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @return
	 */
	public final static boolean openDoorControl(String host,String pass) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/device/openDoorControl");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 设备重置
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param delete  删除设备上所有的识别记录、注册照、现场照，人员、特征等所有的数据，清空所有的数据库
					传入true，删除以上所有信息，并删除通过设备配置接口设置的属性
					     传入false，删除以上所有信息，但不删除通过设备配置接口已经设置的属性
	 * @return
	 */
	public final static boolean reset(String host,String pass,Boolean delete) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/device/reset");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("delete", delete.toString());
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	
	/**
	 * 设备重启
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @return
	 */
	public final static boolean restartDevice(String host,String pass) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/restartDevice");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 设置设备时间
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param timestamp Unix毫秒级时间戳
	 * @return
	 */
	public final static boolean setTime(String host,String pass,Long timestamp) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/setTime");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("timestamp", timestamp.toString());
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 修改屏幕方向
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param orientation 1：横屏，2：竖屏
	 * @return
	 */
	public static final boolean setScreenOrt(String host,String pass,String orientation) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/setScreenOrt");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("orientation", orientation);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 修改Logo
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param imgBase64 图片base64不加头部，如：data:image/jpg;base64,
								    若要恢复设备默认logo，请传入-1
	 * @return
	 */
	public static final boolean changeLogo(String host,String pass,String imgBase64) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/changeLogo");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("imgBase64", imgBase64);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 获取设备序列号
	 * @param host 设备IP
	 * @return 设备序列号 失败返回null
	 */
	public static final String getDeviceKey(String host) {
		StringBuffer sb = new StringBuffer("http://").append(host)
				.append(":8090/getDeviceKey");
		JSONObject json = HttpTool.post(sb.toString(), null);
		if(json!=null&&json.getBoolean("success")) {
			return json.getString("data");
		}else {
			return null;
		}
	}
	/**
	 * 设置设备密码
	 * @param host 设备IP
	 * @param oldPwd 设备旧密码
	 * @param newPwd 设备新密码 （初始化密码时，旧密码和新密码相同）
	 * @return
	 */
	public static final boolean setPassWord(String host,String oldPwd,String newPwd) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/setPassWord");
		final Map<String, String> params = new HashMap<>();
		params.put("oldPass", oldPwd);
		params.put("newPass", newPwd);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 设备配置
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param config 设备配置Bean
	 * @return
	 */
	public static final boolean setConfig(String host,String pass,final EquipmentConfigBean config) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/setConfig");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("config", JSONObject.fromObject(config).toString());
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	

}
