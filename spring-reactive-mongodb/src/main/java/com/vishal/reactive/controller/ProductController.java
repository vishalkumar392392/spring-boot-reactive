package com.vishal.reactive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vishal.reactive.dto.ProductDto;
import com.vishal.reactive.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping
	public Flux<ProductDto> products() {
		return service.getProducts();
	}

	@GetMapping("/{id}")
	public Mono<ProductDto> productById(@PathVariable(value = "id") String id) {
		return service.getProduct(id);
	}

	@GetMapping("/productRange")
	public Flux<ProductDto> productInRange(@RequestParam("min") double min, @RequestParam("max") double max) {
		return service.getProductInRange(min, max);
	}

	@PutMapping("/update/{id}")
	public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDto,
			@PathVariable(value = "id") String id) {
		return service.updateProduct(productDto, id);
	}

	@PostMapping
	public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDto) {
		return service.saveProduct(productDto);
	}
	
	@DeleteMapping("/delete/{id}")
	public Mono<Void> deleteProduct(@PathVariable String id){
		return service.deleteProduct(id);
	}
}
