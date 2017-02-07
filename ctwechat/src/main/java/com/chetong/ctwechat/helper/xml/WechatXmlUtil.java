package com.chetong.ctwechat.helper.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.ui.ModelMap;

public class WechatXmlUtil {

	public static ModelMap parseXml(HttpServletRequest request) throws IOException, DocumentException {
		// 将解析结果存储在HashMap中
		ModelMap map = new ModelMap();

		// 从request中取得输入流
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;

		return map;
	}

	// 去掉获取值中的<![CDATA[location]]>的标识
	public static String killCDATAsign(String str) {
		String result = str;

		if (str != null && str.indexOf("]]>") != -1) {
			result = str.substring(9, str.length() - 3);
		}

		return result;
	}

	
}
