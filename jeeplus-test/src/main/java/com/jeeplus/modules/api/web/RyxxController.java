package com.jeeplus.modules.api.web;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.api.bean.ApiResult;
import com.jeeplus.modules.jcda_ryxx.entity.Jcdaygxx;
import com.jeeplus.modules.jcda_ryxx.service.JcdaygxxService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jeeplus.cmd.HttpTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author WangGuipin
 * @ClassName RyxxController
 * @Description TODO
 * @create 2019-05-11 9:22
 * @desc 人员信息
 **/

@RestController
@RequestMapping("api/Ryxx")
public class RyxxController extends BaseController {

    @Autowired
    private JcdaygxxService jcdaygxxService;
    /*
    服务器对外提供接口，人员信息设备绑定添加接口App调用
     */
    @RequestMapping(value = "ryxxAdd",method= RequestMethod.POST)
    public ApiResult ryxxAdd(Jcdaygxx jcdaygxx, HttpServletRequest request, Model model){
        ApiResult apiResult  = new ApiResult();
        try {
            jcdaygxx.setId(null);
            //新增表单数据
            jcdaygxx.setZdy5("1");
            jcdaygxxService.saveAppUser(jcdaygxx);
            apiResult.setSuccess(true);
            apiResult.setCode(0);
            apiResult.setMessage("人员信息保存成功");
        }catch (Exception e){
            apiResult.setSuccess(true);
            apiResult.setCode(-1);
            apiResult.setMessage("请求异常");
        }
        return   apiResult;
    }
    /*
       服务器对外提供接口，人员信息数据
     */
    @ResponseBody
    @RequestMapping(value = "ryxxData",method = RequestMethod.GET)
    public Map<String, Object> ryxxData(Jcdaygxx jcdaygxx){
        Map<String,Object> map = new HashMap<>();
        jcdaygxx.setZdy5("1");
        List<Jcdaygxx> listRyxxData = jcdaygxxService.findList(jcdaygxx);
        map.put("mapRyxx",listRyxxData);
        return map;
    }
    /*
       云服务对外提供接口，改变属性Zdy5标记
     */
    @RequestMapping(value = "updateZdy5State",method = RequestMethod.POST)
    public String updateZdy5State(HttpServletRequest request){
        String json = getBody(request);
        jcdaygxxService.updateYgxxState(json);
        return null;
    }
    /*
    本地同步服务器数据
     */
    @RequestMapping(value = "nativeRyxxAdd",method = RequestMethod.GET)
    public ApiResult nativeRyxxAdd(){
        Map<String,String> map = new HashMap<>();
        Map<String,String> mapryxx = new HashMap<>();

        List<String> list = new ArrayList<>();
        ApiResult apiResult  = new ApiResult();

        JSONObject RyxxData= HttpTool.get("http://114.55.130.67:8080/qigukeji/api/Ryxx/ryxxData",null);
        String strRyxx = RyxxData.get("mapRyxx").toString();
        //使用JSONArray
        JSONArray jsonArray=JSONArray.fromObject(strRyxx);
        try {
        for (int i = 0;i<jsonArray.size();i++){
            //获得jsonArray的第一个元素

            Object o=jsonArray.get(i);
            JSONObject jsonObject2=JSONObject.fromObject(o);
            Jcdaygxx jcdaygxx = (Jcdaygxx) JSONObject.toBean(jsonObject2, Jcdaygxx.class);
           // jcdaygxx.setId(null);
            jcdaygxxService.saveAdd(jcdaygxx);
            //取出保存成功的用户信息id
            String yhxxId = jcdaygxx.getId();
            logger.debug("我是回调员工"+yhxxId);
            System.out.println("我是回到员工id"+yhxxId);
            //修改当前id对应的状态
            sendHttpPost("http://114.55.130.67:8080/qigukeji/api/Ryxx/updateZdy5State",yhxxId);

            /*
            人员信息同步到设备中
             */
            String id = jcdaygxx.getCode();
            String idcardNum = jcdaygxx.getCardno();
            String name = jcdaygxx.getName();
            String img = jcdaygxx.getZdy2();//图片uri
            String host = jcdaygxx.getZdy3();//设备ip地址


            mapryxx.put("name",name);
            mapryxx.put("idcardNum",idcardNum);
            mapryxx.put("id",id);

            JSONObject mapryxxStr = JSONObject.fromObject(mapryxx);
            map.put("pass","12345678");
            map.put("person",mapryxxStr.toString());

            StringBuffer sb = new StringBuffer("http://").append(host).append(":8090")
                    .append("/person/create");
            JSONObject json = HttpTool.post(sb.toString(), map);
            logger.debug("我是设备回调数据"+json);

            /*
            图片注册
             */
            //网络图片转base64
            String base64_str = encodeImageToBase64(new URL(img));
            String imgStr=base64_str.substring(base64_str.indexOf("base64,")+"base64,".length());
            logger.debug("base64_str输出"+base64_str);
            String  faceId = UUID.randomUUID().toString().replaceAll("-", "");
            create(host,"12345678", id, faceId,base64_str);

            apiResult.setSuccess(true);
            apiResult.setCode(0);
            apiResult.setMessage("success");
        }
        }catch (Exception e){
            apiResult.setSuccess(false);
            apiResult.setCode(-1);
            apiResult.setMessage("请求异常");
        }
        return apiResult;
    }


    /*
     * 接口post请求 body中传输数据
     */
    public static String sendHttpPost(String url, String body) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(body));

        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);

        response.close();
        httpClient.close();
        return responseContent;
    }

    /*
     * 接口post请求 body中传输数据,数据类型Content-Type：application/x-www-form-urlencoded
     */
    public static String sendHttpPostX(String url, String body) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setEntity(new StringEntity(body));

        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine().getStatusCode() + "\n");
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseContent);

        response.close();
        httpClient.close();
        return responseContent;
    }
    /**
     * 获取request中的数据
     * @param request
     * @return
     */
    public static String getBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            String line = null;
            InputStream inputStream;
            inputStream = request.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            //reader = request.getReader();
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    /**
     * 将网络图片编码为base64
     *
     * @param url
     * @return
     * *@throws BusinessException
     */
    public static String encodeImageToBase64(URL url) throws Exception {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        System.out.println("图片的路径为:" + url.toString());
        //打开链接
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"
            conn.setRequestMethod("GET");
        //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
        //使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
         //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
        //关闭输入流
            inStream.close();
            byte[] data = outStream.toByteArray();
        //对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(data);
            System.out.println("网络文件[{}]编码成base64字符串:[{}]"+url.toString()+base64);
            return base64;//返回Base64编码过的字节数组字符串
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("图片上传失败,请联系客服!");
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
        System.out.println("我是图片注册回调数据"+json);
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
