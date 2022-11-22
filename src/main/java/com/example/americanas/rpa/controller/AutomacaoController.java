package com.example.americanas.rpa.controller;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AutomacaoController {
	
	@RequestMapping(value = "/americanas", method = RequestMethod.GET)
	public @ResponseBody void buscarWebcams() {
		
		System.setProperty("webdriver.chrome.driver", "src\\drive\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		
	}

}
