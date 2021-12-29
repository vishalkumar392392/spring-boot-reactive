package com.vishal.reactive.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;

import com.vishal.reactive.dto.ProductDto;
import com.vishal.reactive.repository.ProductRepository;
import com.vishal.reactive.utils.AppUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;

	public Flux<ProductDto> getProducts() {
		return repository.findAll().map(AppUtils::entityToDTO);
	}

	public Mono<ProductDto> getProduct(String id) {
		return repository.findById(id).map(AppUtils::entityToDTO);
	}

	public Flux<ProductDto> getProductInRange(double min, double max) {
		return repository.findByPriceBetween(Range.closed(min, max));
	}

	public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto) {
		return productDto.map(AppUtils::dtoToEntity).flatMap(repository::insert).map(AppUtils::entityToDTO);
	}

	public Mono<ProductDto> updateProduct(Mono<ProductDto> productDto, String id) {
		return repository.findById(id)
				.flatMap(p -> productDto.map(AppUtils::dtoToEntity)).doOnNext(e -> e.setId(id))
				.flatMap(repository::save).map(AppUtils::entityToDTO);
	}
	
	public Mono<Void> deleteProduct(String id){
		return repository.deleteById(id);
	}

}
