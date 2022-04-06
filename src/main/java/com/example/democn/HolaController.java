package com.example.democn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {

	@GetMapping("/")
	public String index() {
		return "Hola Mundo desde Spring Boot!";
	}

}
