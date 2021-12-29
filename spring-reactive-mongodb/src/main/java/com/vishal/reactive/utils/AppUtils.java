package com.vishal.reactive.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;

import com.vishal.reactive.dto.ProductDto;
import com.vishal.reactive.entity.Product;

public class AppUtils {

	public static ProductDto entityToDTO(Product product) {

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return mapper.map(product, ProductDto.class);
	}

	public static Product dtoToEntity(ProductDto productDto) {

		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		return product;
	}
}
