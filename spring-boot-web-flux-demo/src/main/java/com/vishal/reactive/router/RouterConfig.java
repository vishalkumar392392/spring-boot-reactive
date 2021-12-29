package com.vishal.reactive.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vishal.reactive.handler.CustomerHandler;

@Configuration
public class RouterConfig {

	@Bean
	public RouterFunction<ServerResponse> routerFunction(@Autowired CustomerHandler handler) {
		return RouterFunctions.route()
				.GET("/router/customers", handler::loadCustomers)
				.GET("/router/customersStream", handler::loadCustomersStream)
				.GET("/router/customersStream/{input}", handler::findCustomer)
				.build();
	}
}
