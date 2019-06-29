package com.learnreactivespring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiRestController {
	
	 @GetMapping("/greeting")
	public String hello()
	{
		return "hi";
	}

}
