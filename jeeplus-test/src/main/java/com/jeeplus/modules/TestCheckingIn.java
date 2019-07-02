package com.jeeplus.modules;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.jcda_ryxx.entity.Jcdaygxx;
import com.jeeplus.modules.jcda_ryxx.entity.Syjg;
import com.jeeplus.modules.jcda_ryxx.mapper.JcdaygxxMapper;
import com.jeeplus.modules.jcda_sbgl_view.entity.JcdaSbglView;
import com.jeeplus.modules.jcda_sbkqxx.entity.JcdaSbmcView1;
import com.jeeplus.modules.jcda_sbkqxx.entity.Jcdasbkqxx;
import com.jeeplus.modules.jcda_sbkqxx.mapper.JcdasbkqxxMapper;

@RestController
@RequestMapping("test/checkingin/")
public class TestCheckingIn {

	@Autowired
	private JcdaygxxMapper jcdaygxxMapper;
	@Autowired
	private JcdasbkqxxMapper jcdasbkqxxMapper;
	
	@GetMapping("generate")
	public String generateTestData() {
		JcdaSbglView sb = new JcdaSbglView("1683492ef9c14d688e60467348d9a530");
		JcdaSbmcView1 sb1 = new JcdaSbmcView1("1683492ef9c14d688e60467348d9a530");
		generateKq(sb1, generateUser(sb));
		return "success";
	}
	
	private List<Jcdaygxx> generateUser(JcdaSbglView sb) {
		List<Jcdaygxx> list = Lists.newArrayList();
		Syjg jg = new Syjg("1acf96e3ad554057bfe585c03f598200");
		Jcdaygxx jcdaygxx = null;
		for(int i=0;i<2000;i++) {
			jcdaygxx = new Jcdaygxx(sb);
			jcdaygxx.setJg(jg);
			jcdaygxx.setId("test-"+i);
			jcdaygxx.setName("test-"+i);
			jcdaygxx.setCardno("code-"+i);
			jcdaygxx.setDelFlag("0");
			jcdaygxx.setPersonstate("在职");
			jcdaygxxMapper.insert(jcdaygxx);
			list.add(jcdaygxx);
		}
		return list;
	}
	
	public void generateKq(JcdaSbmcView1 sb, List<Jcdaygxx> list) {
		String[] dates = {"2019-04-10","2019-04-01","2019-04-02","2019-04-03","2019-04-04","2019-04-05","2019-04-06","2019-04-07","2019-04-08","2019-04-09",
				"2019-04-20","2019-04-11","2019-04-12","2019-04-13","2019-04-14","2019-04-15","2019-04-16","2019-04-17","2019-04-18","2019-04-19",
				"2019-04-30","2019-04-21","2019-04-22","2019-04-23","2019-04-24","2019-04-25","2019-04-26","2019-04-27","2019-04-28","2019-04-29",};
		List<String> ds = Arrays.asList(dates);
		list.forEach(e->{
			ds.forEach(d->{
				Jcdasbkqxx k = new Jcdasbkqxx();
				k.setId(UUID.randomUUID().toString());
				k.setCode(e.getCardno());
				k.setName(e.getName());
				k.setZdy1(e.getId());
				k.setDate(DateUtils.parseDate(d+" 08:10:00"));
				k.setZdy4("/userfiles/record/2019/04/26ecddb41dae4bfaa23b7e84706e2105_1555418675349.jpg");
				k.setSb(sb);
				Jcdasbkqxx m = new Jcdasbkqxx();
				m.setId(UUID.randomUUID().toString());
				m.setCode(e.getCardno());
				m.setName(e.getName());
				m.setZdy1(e.getId());
				m.setDate(DateUtils.parseDate(d+" 11:40:00"));
				m.setZdy4("/userfiles/record/2019/04/26ecddb41dae4bfaa23b7e84706e2105_1555418675349.jpg");
				m.setSb(sb);
				Jcdasbkqxx n = new Jcdasbkqxx();
				n.setId(UUID.randomUUID().toString());
				n.setCode(e.getCardno());
				n.setName(e.getName());
				n.setZdy1(e.getId());
				n.setDate(DateUtils.parseDate(d+" 13:40:00"));
				n.setZdy4("/userfiles/record/2019/04/26ecddb41dae4bfaa23b7e84706e2105_1555418675349.jpg");
				n.setSb(sb);
				Jcdasbkqxx o = new Jcdasbkqxx();
				o.setId(UUID.randomUUID().toString());
				o.setCode(e.getCardno());
				o.setName(e.getName());
				o.setZdy1(e.getId());
				o.setDate(DateUtils.parseDate(d+" 17:40:00"));
				o.setZdy4("/userfiles/record/2019/04/26ecddb41dae4bfaa23b7e84706e2105_1555418675349.jpg");
				o.setSb(sb);
				jcdasbkqxxMapper.insert(k);
				jcdasbkqxxMapper.insert(m);
				jcdasbkqxxMapper.insert(n);
				jcdasbkqxxMapper.insert(o);
			});
		});
	}
}
