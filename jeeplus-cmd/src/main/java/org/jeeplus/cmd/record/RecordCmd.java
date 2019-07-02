package org.jeeplus.cmd.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jeeplus.cmd.HttpTool;
import org.jeeplus.cmd.employee.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 记录命令
 * @author Jin
 *
 */
public class RecordCmd {

	/**
	 * 刷卡记录删除
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param time 时间节点 <p>删除所传日期时间点前的所有人脸识别记录及现场照 传入时间格式为（年-月-日 时:分:秒）2017-07-15 12:05:00</p>
	 * @return
	 */
	public final static boolean deleteICRecords(String host,String pass,String time) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/deleteICRecords");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("time", time);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 识别记录删除
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param time 时间节点 <p>删除所传日期时间点前的所有人脸识别记录及现场照 传入时间格式为（年-月-日 时:分:秒）2017-07-15 12:05:00</p>
	 * @return
	 */
	public final static boolean deleteRecords(String host,String pass,String time) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/deleteRecords");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("time", time);
		JSONObject json = HttpTool.post(sb.toString(), params);
		return json!=null&&json.getBoolean("success");
	}
	/**
	 * 刷卡记录查询
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param personId 查询指定id的人员识别记录
					　　 传入-1可查询所有人员的识别记录，包括陌生人
					　　  传入STRANGERBABY，可查询所有陌生人/识别失败记录
	 * @param length 传入-1为不分页　若不传-1，请务必大于0
	 * @param index 页码，从0开始
	 * @param startTime 若不按时间查询，请分别传入0 若需要按时间查询，请按照如下格式（年-月-日 时:分:秒）：
						2017-07-15 12:05:00
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public final static CardRecord findICRecords(String host,String pass,String personId,
			Integer length,Integer index,String startTime,String endTime) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/findICRecords");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("personId", personId);
		params.put("length",length.toString());
		params.put("index", index==null?"0":index.toString());
		params.put("startTime",startTime);
		params.put("endTime", endTime);
		JSONObject json = HttpTool.get(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			json = json.getJSONObject("data");
			CardRecord record = new CardRecord();
			record.setPageInfo((PageInfo) JSONObject.toBean(json.getJSONObject("pageInfo"), PageInfo.class));
			record.setRecords((ArrayList<Card>) JSONArray.toList(json.getJSONArray("records"), Card.class));
			return record;
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	/**
	 * 识别记录查询 
	 * @param host 设备IP
	 * @param pass 设备密码
	 * @param personId 查询指定id的人员识别记录
					　　 传入-1可查询所有人员的识别记录，包括陌生人
					　　  传入STRANGERBABY，可查询所有陌生人/识别失败记录
	 * @param length 传入-1为不分页　若不传-1，请务必大于0
	 * @param index 页码，从0开始
	 * @param startTime 若不按时间查询，请分别传入0 若需要按时间查询，请按照如下格式（年-月-日 时:分:秒）：
						2017-07-15 12:05:00
	 * @param endTime
	 * @return
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public final static PhotoRecord findRecords(String host,String pass,String personId,
			Integer length,Integer index,String startTime,String endTime) {
		StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
				.append("/findRecords");
		final Map<String, String> params = new HashMap<>();
		params.put("pass", pass);
		params.put("personId", personId);
		params.put("length",length.toString());
		params.put("index", index==null?"0":index.toString());
		params.put("startTime",startTime);
		params.put("endTime", endTime);
		JSONObject json = HttpTool.get(sb.toString(), params);
		if(json!=null&&json.getBoolean("success")) {
			json = json.getJSONObject("data");
			PhotoRecord record = new PhotoRecord();
			record.setPageInfo((PageInfo) JSONObject.toBean(json.getJSONObject("pageInfo"), PageInfo.class));
			record.setRecords((ArrayList<Photo>) JSONArray.toList(json.getJSONArray("records"), Photo.class));
			return record;
		}else {
			throw new RuntimeException(json.getString("msg"));
		}
	}
	
	public static void main(String[] args) {
		findRecords("192.168.1.253", "12345678", "-1", 800, 0, "2019-04-09 16:10:58", "0");
	}
}
