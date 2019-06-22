package com.training.mjunction.product.composite.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.training.mjunction.product.composite.domain.Product;

@FeignClient("product-catalog")
public interface ProductCatalogClient {

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products")
	List<Product> findAll();

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/{name}")
	Product findByName(@PathVariable("name") String name);

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products")
	List<Product> getProdcts();

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/{category}/{name}")
	Product findByCategoryAndName(@PathVariable("category") String category, @PathVariable("name") String name);

	@RequestMapping(method = RequestMethod.PUT, value = "/api/v1/products")
	Product add(@RequestBody Product product);

	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/products/{name}")
	Product update(@PathVariable("name") String name, @RequestBody Product product);

	@RequestMapping(method = RequestMethod.DELETE, value = "/api/v1/products/{name}")
	void delete(@PathVariable("name") String name);
}
