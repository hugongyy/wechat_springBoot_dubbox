package com.chetong.ctwechat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base")
public class HelloController {
	
	@RequestMapping("/hello")
	public String hello(){
		return "hello";
	}

}
