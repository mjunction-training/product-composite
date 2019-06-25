package com.training.mjunction.product.composite.clients;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.training.mjunction.product.composite.domain.Product;

@FeignClient(name = "product-catalog", fallback = ProductCatalogClientFallback.class)
public interface ProductCatalogClient {

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products")
	List<Product> findAll(@RequestHeader Map<String, String> headers);

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/{name}")
	Product findByName(@PathVariable("name") String name, @RequestHeader Map<String, String> headers);

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/{category}/{name}")
	Product findByCategoryAndName(@PathVariable("category") String category, @PathVariable("name") String name,
			@RequestHeader Map<String, String> headers);

	@RequestMapping(method = RequestMethod.PUT, value = "/api/v1/products")
	Product add(@RequestBody Product product, @RequestHeader Map<String, String> headers);

	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/products/{name}")
	Product update(@PathVariable("name") String name, @RequestBody Product product,
			@RequestHeader Map<String, String> headers);

	@RequestMapping(method = RequestMethod.DELETE, value = "/api/v1/products/{name}")
	void delete(@PathVariable("name") String name, @RequestHeader Map<String, String> headers);
}
