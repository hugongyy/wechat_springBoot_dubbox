package com.chetong.ctwechat.service.impl;

import org.springframework.stereotype.Service;

import com.chetong.ctwechat.entity.model.BasesModel;
import com.chetong.ctwechat.service.PushMessageService;
@Service("pushMessageService")
public class PushMessageServiceImpl implements PushMessageService {

	@Override
	public BasesModel savePushMsg4Wechat(Long userId, String content, String createBy) {
		
		return null;
	}

}
