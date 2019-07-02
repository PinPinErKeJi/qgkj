/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ryxx.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.jcda_ryxx.entity.Jcdaygxx;
import com.jeeplus.modules.jcda_ryxx.entity.Photo;
import com.jeeplus.modules.jcda_ryxx.service.JcdaygxxService;

/**
 * 员工信息Controller
 * @author ww
 * @version 2019-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_ryxx/jcdaygxx")
public class JcdaygxxController extends BaseController {

	@Autowired
	private JcdaygxxService jcdaygxxService;
	
	@ModelAttribute
	public Jcdaygxx get(@RequestParam(required=false) String id) {
		Jcdaygxx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdaygxxService.get(id);
		}
		if (entity == null){
			entity = new Jcdaygxx();
		}
		return entity;
	}
	
	/**
	 * 员工信息列表页面
	 */
	@RequiresPermissions("jcda_ryxx:jcdaygxx:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdaygxx jcdaygxx, Model model) {
		model.addAttribute("jcdaygxx", jcdaygxx);
		return "modules/jcda_ryxx/jcdaygxxList";
	}
	
	 /**
	 * 员工信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ryxx:jcdaygxx:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdaygxx jcdaygxx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdaygxx> page = jcdaygxxService.findPage(new Page<Jcdaygxx>(request, response), jcdaygxx); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工信息表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_ryxx:jcdaygxx:view","jcda_ryxx:jcdaygxx:add","jcda_ryxx:jcdaygxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdaygxx jcdaygxx, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdaygxx", jcdaygxx);
		return "modules/jcda_ryxx/jcdaygxxForm";
	}
	

	/**
	 * 离职
	 * @param rids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("dimission")
	public AjaxJson dimission(String rids) {
		AjaxJson j = new AjaxJson();
		try {
			jcdaygxxService.dimission(rids);
			j.setMsg("离职成功");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	/**
	 * 重新注册
	 * @param rids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("reRegister")
	public AjaxJson reRegister(String rids) {
		AjaxJson j = new AjaxJson();
		try {
			jcdaygxxService.reRegister(rids);
			j.setMsg("重新注册成功");
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	/**
	 * 注册
	 * @param rid
	 * @return
	 */
	@RequiresPermissions(value={"jcda_ryxx:jcdaygxx:add","jcda_ryxx:jcdaygxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "goToRegister")
	public String goToRegister(String rid, Model model) {
		Jcdaygxx jcdasbyhxx = jcdaygxxService.get(rid);
		model.addAttribute("jcdasbyhxx", jcdasbyhxx);
		model.addAttribute("photo", jcdaygxxService.findPhotoByPid(rid));
		return "modules/jcda_ryxx/register";
	}
	/** 图片注册 */
	@ResponseBody
	@RequestMapping("registerImage")
	public AjaxJson registerImage(String sbId, String personId,String faceId, String imgStr) {
		AjaxJson j = new AjaxJson();
		try {
			imgStr=imgStr.substring(imgStr.indexOf("base64,")+"base64,".length());
			Photo p = jcdaygxxService.registerImage(personId, sbId, imgStr,faceId);
			j.setMsg("注册成功");
			j.setAction(p.getFaceId());
			j.setUrl(p.getPath());
			j.setSuccess(true);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	/** 联机注册 */
	@ResponseBody
	@RequestMapping("registerOnline")
	public AjaxJson registerOnline(String sbId, String personId) {
		AjaxJson j = new AjaxJson();
		try {
			jcdaygxxService.registerOnline(sbId, personId);
			j.setMsg("设备摄像头已打开，请按屏幕指示操作。");
			j.setSuccess(true);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 获取联机注册信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getRegisterOnline")
	public AjaxJson getRegisterOnline(String sbId, String personId) {
		AjaxJson j = new AjaxJson();
		try {
			Photo p =jcdaygxxService.getRegisterOnline(sbId, personId);
			j.setSuccess(true);
			j.setMsg("获取成功");
			j.setAction(p.getFaceId());
			j.setUrl(p.getPath());
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	@ResponseBody
	@RequestMapping("deleteImage")
	public AjaxJson deleteImage(String sbId, String faceId) {
		AjaxJson j = new AjaxJson();
		try {
			j.setSuccess(true);
			jcdaygxxService.deleteImage(sbId, faceId);
		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		return j;
	}
	
	/** 上传图片(不做处理) */
	@ResponseBody
	@RequestMapping("acceptImage")
	public AjaxJson acceptImage() {
		AjaxJson j = new AjaxJson();
		
		return j;
	}
	/**
	 * 保存员工信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_ryxx:jcdaygxx:add","jcda_ryxx:jcdaygxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdaygxx jcdaygxx, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdaygxx);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdaygxxService.save(jcdaygxx);//保存
		j.setSuccess(true);
		j.setMsg("保存员工信息成功");
		return j;
	}

	
	/**
	 * 批量删除员工信息
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ryxx:jcdaygxx:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdaygxxService.delete(jcdaygxxService.get(id));
		}
		j.setMsg("删除员工信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ryxx:jcdaygxx:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdaygxx jcdaygxx, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdaygxx> page = jcdaygxxService.findPage(new Page<Jcdaygxx>(request, response, -1), jcdaygxx);
    		new ExportExcel("员工信息", Jcdaygxx.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_ryxx:jcdaygxx:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdaygxx> list = ei.getDataList(Jcdaygxx.class);
			for (Jcdaygxx jcdaygxx : list){
				try{
					jcdaygxxService.save(jcdaygxx);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ryxx:jcdaygxx:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工信息数据导入模板.xlsx";
    		List<Jcdaygxx> list = Lists.newArrayList(); 
    		new ExportExcel("员工信息数据", Jcdaygxx.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}