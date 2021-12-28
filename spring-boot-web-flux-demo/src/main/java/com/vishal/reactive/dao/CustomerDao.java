package com.vishal.reactive.dao;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.vishal.reactive.dto.Customer;

import reactor.core.publisher.Flux;

@Component
public class CustomerDao {

	private static void sleepExecution(int i) {

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Customer> getCustomers() {
		return IntStream.rangeClosed(1, 10).peek(i -> System.out.println("processing count : " + i))
				.peek(CustomerDao::sleepExecution).mapToObj(i -> new Customer(i, "customer_" + i))
				.collect(Collectors.toList());
	}

	public Flux<Customer> getCustomersStream() {
		return Flux.range(1, 10).delayElements(Duration.ofSeconds(1))
				.doOnNext(i -> System.out.println("processing count in stream flow : " + i))
				.map(i -> new Customer(i, "customer_" + i));

	}

}
