package com.chetong.ctwechat.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class HelloController {

	Log log = LogFactory.getLog(HelloController.class);
	@RequestMapping("/hello")
	public String hello() {
		BigDecimal hour = new BigDecimal(1000 * 60 * 60);
		BigDecimal p = new BigDecimal("0.18").multiply(hour);		
		BigDecimal d = new BigDecimal("600000");
		int k = p.compareTo(d);
		try {
			int a = 1/0;
		} catch (Exception e) {
//			throw e;
			log.error("1111", e);
		}	
		
		// return "hello";
		return p.toString() + ",k=" + k;
	}

}
