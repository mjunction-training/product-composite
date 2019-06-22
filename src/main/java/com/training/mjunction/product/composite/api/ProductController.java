package com.training.mjunction.product.composite.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.mjunction.product.composite.clients.ProductCatalogClient;
import com.training.mjunction.product.composite.domain.Product;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class ProductController {

	@Autowired
	private ProductCatalogClient productCatalogClient;

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products")
	public List<Product> findAll() {
		log.info("Product.findAll()");
		return productCatalogClient.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/{name}")
	public Product findByName(@PathVariable("name") final String name) {
		log.info(String.format("Product.findByName(%s)", name));
		return productCatalogClient.findByName(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/{category}/{name}")
	public Product findByCategoryAndName(@PathVariable("category") final String category,
			@PathVariable("name") final String name) {
		log.info(String.format("Product.findByCategoryAndName(%s, %s)", name, category));
		return productCatalogClient.findByCategoryAndName(category, name);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/api/v1/products")
	public Product add(@RequestBody final Product product) {
		log.info(String.format("Product.add(%s)", product));
		return productCatalogClient.add(product);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/products/{name}")
	public Product update(@PathVariable("name") final String name, @RequestBody final Product product) {
		log.info(String.format("Product.update(%s, %s)", name, product));
		return productCatalogClient.update(name, product);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/api/v1/products/{name}")
	public void delete(@PathVariable("name") final String name) {
		log.info(String.format("Product.delete(%s)", name));
		productCatalogClient.delete(name);
	}

}