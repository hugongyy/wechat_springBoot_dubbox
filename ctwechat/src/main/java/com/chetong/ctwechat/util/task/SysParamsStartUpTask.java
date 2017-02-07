package com.chetong.ctwechat.util.task;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.chetong.ctwechat.entity.mapping.VersionInfo;
import com.chetong.ctwechat.service.RankService;
import com.chetong.ctwechat.service.SysSmsService;
import com.chetong.ctwechat.service.WechatService;
import com.chetong.ctwechat.service.impl.RankServiceImpl;
import com.chetong.ctwechat.service.impl.SysSmsServiceImpl;
import com.chetong.ctwechat.service.impl.WechatServiceImpl;

/**
 * 自动加载数据库version_info表中的一些参数.
 * 
 * @author Administrator
 *
 */
@Component
public class SysParamsStartUpTask implements CommandLineRunner {
	private Log log = LogFactory.getLog(SysParamsStartUpTask.class);

	@Autowired
	private SysSmsService sysSmsService;

	@Autowired
	private WechatService wechatService;

	@Autowired
	private RankService rankService;

	@Override
	public void run(String... arg0) throws Exception {
		List<VersionInfo> viList = sysSmsService.queryVersionInfoList();
		for (VersionInfo vi : viList) {
			if ("sendSms".equals(vi.getVersionType())) {
				SysSmsServiceImpl.sendSmsChannel = vi.getVersionCode();
				log.info("sendSmsChannel=" + SysSmsServiceImpl.sendSmsChannel);
			}
			if ("receiveSms".equals(vi.getVersionType())) {
				SysSmsServiceImpl.receiveSmsChannel = vi.getVersionCode();
				log.info("receiveSmsChannel=" + SysSmsServiceImpl.receiveSmsChannel);
			}
		}
		// 特殊地区机构
		WechatServiceImpl.spAreas = wechatService.querySpAreas();
	}

}
