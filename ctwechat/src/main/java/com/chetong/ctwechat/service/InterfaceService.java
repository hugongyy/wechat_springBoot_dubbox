package com.chetong.ctwechat.service;

import java.util.List;

import org.springframework.ui.ModelMap;

import com.chetong.ctwechat.entity.sms.SendSmsResp;

public interface InterfaceService {

	String locationSave(ModelMap map);

	String textSave(ModelMap map);

	String imageSave(ModelMap map);

	String locationHandle(ModelMap map);

}
