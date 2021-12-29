package com.vishal.reactive.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vishal.reactive.dao.CustomerDao;
import com.vishal.reactive.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

	@Autowired
	private CustomerDao dao;
	
	public Mono<ServerResponse> loadCustomers(ServerRequest request){
		Flux<Customer> customerList = dao.getCustomerList();
		return ServerResponse.ok().body(customerList,Customer.class);
	}
	
	public Mono<ServerResponse> loadCustomersStream(ServerRequest request){
		Flux<Customer> customersStream = dao.getCustomersStream();
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(customersStream, Customer.class);
	}
	
	public Mono<ServerResponse> findCustomer(ServerRequest request){
		Mono<Customer> customersStream = dao.getCustomerList()
				.filter(i->i.getId()== Integer.parseInt(request.pathVariable("input"))).next();
		return ServerResponse.ok().body(customersStream, Customer.class);
	}
}
