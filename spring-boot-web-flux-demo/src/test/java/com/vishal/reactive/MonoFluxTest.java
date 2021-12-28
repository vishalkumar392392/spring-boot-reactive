package com.vishal.reactive;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

	@Test
	public void testMono() {
		Mono<?> monoString = Mono.just("vishal")
//				.then(Mono.error(new RuntimeException("Exception ocuured")))
				.log();
		monoString.subscribe(System.out::println, (e) -> System.out.println(e.getMessage()));
	}
	
	@Test
	public void testFlux() {
		Flux<String> fluxString = Flux.just("vishal","vikas","ajith")
				.concatWithValues("prabhas")
				.concatWith(Flux.error(new RuntimeException("Exception ocuured in flux")))
				.log();
		fluxString.subscribe(System.out::println,  (e) -> System.out.println(e.getMessage()));
	}
}
