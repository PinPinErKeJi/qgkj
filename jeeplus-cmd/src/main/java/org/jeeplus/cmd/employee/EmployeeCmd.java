package org.jeeplus.cmd.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jeeplus.cmd.HttpTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 员工接口
 * @author Jin
 *
 */
public final class EmployeeCmd {
	/**
	 * 人员信息查询
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param id 员工id
	 * @return
	 */
	public final static CallBackPersonInfo find(String host,String pass,String id) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/person/find");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("id", id);
		JSONObject json = HttpTool.get(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			return (CallBackPersonInfo) JSONObject.toBean(json.getJSONArray("data").getJSONObject(0), CallBackPersonInfo.class);
		}else {
			return null;
		}
	}
	/**
	 * 分页查询
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param length 查询长度 默认1000
	 * @param index 页码从0开始
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public final static SearchEmployeeCollection findByPage(String host,String pass,
			Integer length,Integer index){
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/person/findByPage");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("personId", "-1");
		params.put("length",length==null?"1000":length.toString());
		params.put("index", index==null?"0":index.toString());
		JSONObject json = HttpTool.get(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			json = json.getJSONObject("data");
			SearchEmployeeCollection collection = new SearchEmployeeCollection();
			collection.setPageInfo((PageInfo) JSONObject.toBean(json.getJSONObject("pageInfo"), PageInfo.class));
			collection.setPersonInfos((ArrayList<CallBackPersonInfo>) JSONArray.toList(json.getJSONArray("personInfos"), CallBackPersonInfo.class));
			return collection;
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	/**
	 * 删除人员（批量）
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param ids 人员id（逗号,分割） 传入-1时，删除全部
	 * @return 0：删除成功，其他：为失效id
	 */
	public final static String delete(String host,String pass,String ids) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/person/delete");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("id", ids);
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			/*json = json.getJSONObject("data");
			if(json.containsKey("invalid")&&json.getString("invalid").length()>0) {
				return json.getString("invalid");
			}else {
				return "0";
			}*/
			return "0";
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	/**
	 * 人员更新(id,name不可为空，idcardNum为空则清空原来的卡号)
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param person {@link org.jeeplus.cmd.employee.RegisterPersonInfo}
	 * @return
	 */
	public final static CallBackPersonInfo update(String host,String pass,RegisterPersonInfo person) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/person/update");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("person", JSONObject.fromObject(person).toString());
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			return (CallBackPersonInfo) JSONObject.toBean(json.getJSONObject("data"), CallBackPersonInfo.class) ;
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	
	/**
	 * 人员注册
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param person {@link org.jeeplus.cmd.employee.RegisterPersonInfo}
	 * @return
	 */
	public final static CallBackPersonInfo create(String host,String pass,RegisterPersonInfo person) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/person/create");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("person", JSONObject.fromObject(person).toString());
		JSONObject json = HttpTool.post(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			return (CallBackPersonInfo) JSONObject.toBean(json.getJSONObject("data"), CallBackPersonInfo.class) ;
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	
	/**
	 * 人员卡号注册 <p>卡号注册的过程实际上是设备先读取卡号，再将卡号写入人员信息数据库（两个过程合称录入卡号），因此与人员注册时直接填写卡号效果一致</p>
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param personId 人员id（必须是已存在的id）
	 * @return 成功后，设备开始录入卡号，可通过卡号是否写入人员信息库判断卡号注册是否成功
	 */
	public final static boolean icCardRegist(String host,String pass,String personId) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/face/icCardRegist");
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
}
