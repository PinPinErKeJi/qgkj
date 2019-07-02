/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.test.onetomany.dialog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataMainDialog;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataMainDialogMapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild11;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataChild11Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild12;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataChild12Mapper;
import com.jeeplus.modules.test.onetomany.dialog.entity.TestDataChild13;
import com.jeeplus.modules.test.onetomany.dialog.mapper.TestDataChild13Mapper;

/**
 * 票务代理Service
 * @author liugf
 * @version 2019-01-01
 */
@Service
@Transactional(readOnly = true)
public class TestDataMainDialogService extends CrudService<TestDataMainDialogMapper, TestDataMainDialog> {

	@Autowired
	private TestDataChild11Mapper testDataChild11Mapper;
	@Autowired
	private TestDataChild12Mapper testDataChild12Mapper;
	@Autowired
	private TestDataChild13Mapper testDataChild13Mapper;
	
	public TestDataMainDialog get(String id) {
		TestDataMainDialog testDataMainDialog = super.get(id);
		testDataMainDialog.setTestDataChild11List(testDataChild11Mapper.findList(new TestDataChild11(testDataMainDialog)));
		testDataMainDialog.setTestDataChild12List(testDataChild12Mapper.findList(new TestDataChild12(testDataMainDialog)));
		testDataMainDialog.setTestDataChild13List(testDataChild13Mapper.findList(new TestDataChild13(testDataMainDialog)));
		return testDataMainDialog;
	}
	
	public List<TestDataMainDialog> findList(TestDataMainDialog testDataMainDialog) {
		return super.findList(testDataMainDialog);
	}
	
	public Page<TestDataMainDialog> findPage(Page<TestDataMainDialog> page, TestDataMainDialog testDataMainDialog) {
		return super.findPage(page, testDataMainDialog);
	}
	
	@Transactional(readOnly = false)
	public void save(TestDataMainDialog testDataMainDialog) {
		super.save(testDataMainDialog);
		for (TestDataChild11 testDataChild11 : testDataMainDialog.getTestDataChild11List()){
			if (testDataChild11.getId() == null){
				continue;
			}
			if (TestDataChild11.DEL_FLAG_NORMAL.equals(testDataChild11.getDelFlag())){
				if (StringUtils.isBlank(testDataChild11.getId())){
					testDataChild11.setTestDataMain(testDataMainDialog);
					testDataChild11.preInsert();
					testDataChild11Mapper.insert(testDataChild11);
				}else{
					testDataChild11.preUpdate();
					testDataChild11Mapper.update(testDataChild11);
				}
			}else{
				testDataChild11Mapper.delete(testDataChild11);
			}
		}
		for (TestDataChild12 testDataChild12 : testDataMainDialog.getTestDataChild12List()){
			if (testDataChild12.getId() == null){
				continue;
			}
			if (TestDataChild12.DEL_FLAG_NORMAL.equals(testDataChild12.getDelFlag())){
				if (StringUtils.isBlank(testDataChild12.getId())){
					testDataChild12.setTestDataMain(testDataMainDialog);
					testDataChild12.preInsert();
					testDataChild12Mapper.insert(testDataChild12);
				}else{
					testDataChild12.preUpdate();
					testDataChild12Mapper.update(testDataChild12);
				}
			}else{
				testDataChild12Mapper.delete(testDataChild12);
			}
		}
		for (TestDataChild13 testDataChild13 : testDataMainDialog.getTestDataChild13List()){
			if (testDataChild13.getId() == null){
				continue;
			}
			if (TestDataChild13.DEL_FLAG_NORMAL.equals(testDataChild13.getDelFlag())){
				if (StringUtils.isBlank(testDataChild13.getId())){
					testDataChild13.setTestDataMain(testDataMainDialog);
					testDataChild13.preInsert();
					testDataChild13Mapper.insert(testDataChild13);
				}else{
					testDataChild13.preUpdate();
					testDataChild13Mapper.update(testDataChild13);
				}
			}else{
				testDataChild13Mapper.delete(testDataChild13);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TestDataMainDialog testDataMainDialog) {
		super.delete(testDataMainDialog);
		testDataChild11Mapper.delete(new TestDataChild11(testDataMainDialog));
		testDataChild12Mapper.delete(new TestDataChild12(testDataMainDialog));
		testDataChild13Mapper.delete(new TestDataChild13(testDataMainDialog));
	}
	
}