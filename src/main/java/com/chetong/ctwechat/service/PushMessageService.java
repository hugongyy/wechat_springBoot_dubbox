package com.chetong.ctwechat.service;

import com.chetong.ctwechat.entity.model.BasesModel;

public interface PushMessageService {
	BasesModel savePushMsg4Wechat(Long userId, String content, String createBy);
}
