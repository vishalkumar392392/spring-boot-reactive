package com.vishal.reactive;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.vishal.reactive.controller.ProductController;
import com.vishal.reactive.dto.ProductDto;
import com.vishal.reactive.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = ProductController.class)
class SpringReactiveMongodbApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService service;

	@Test
	public void addProductTest() {
		Mono<ProductDto> productDto = Mono.just(new ProductDto("102", "mobile", 1, 10000));
		Mockito.when(service.saveProduct(productDto)).thenReturn(productDto);

		webTestClient.post().uri("/products").body(Mono.just(productDto), ProductDto.class).exchange().expectStatus()
				.isOk();
	}

	@Test
	public void getProductTest() {
		Flux<ProductDto> productDto = Flux.just(new ProductDto("102", "mobile", 1, 10000));
		Mockito.when(service.getProducts()).thenReturn(productDto);

		Flux<ProductDto> responseBody = webTestClient.get().uri("/products").exchange().expectStatus().isOk()
				.returnResult(ProductDto.class).getResponseBody();
		StepVerifier.create(responseBody).expectSubscription().expectNext(new ProductDto("102", "mobile", 1, 10000))
				.verifyComplete();
	}
	
	@Test
	public void getProductByIdTest() {
		Mono<ProductDto> productDto = Mono.just(new ProductDto("102", "mobile", 1, 10000));
		Mockito.when(service.getProduct(Mockito.anyString())).thenReturn(productDto);

		Flux<ProductDto> responseBody = webTestClient.get().uri("/products/102").exchange().expectStatus().isOk()
				.returnResult(ProductDto.class).getResponseBody();
		StepVerifier.create(responseBody).expectSubscription()
		.expectNextMatches(p->p.getName().equals("mobile"))
				.verifyComplete();
	}

}
