package kr.spring.tiles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TilesController {
	@GetMapping("/")
	public String init() {
		return "redirect:/main.do";
	}
	@GetMapping("/main.do")
	public String viewMain() {
		return "main";//Tiles 식별자
	}
	@GetMapping("/company.do")
	public String viewCompany() {
		return "company";//Tiles 식별자
	}
	@GetMapping("/product.do")
	public String viewProduct() {
		return "product";//Tiles 식별자
	}
}
