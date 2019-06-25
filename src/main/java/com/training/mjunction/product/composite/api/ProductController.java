package com.training.mjunction.product.composite.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.training.mjunction.product.composite.clients.ProductCatalogClient;
import com.training.mjunction.product.composite.clients.ProductCatalogClientFallback;
import com.training.mjunction.product.composite.domain.Product;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class ProductController {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private ProductCatalogClient productCatalogClient;

	@Autowired
	private ProductCatalogClientFallback productCatalogClientFallback;

	@HystrixCommand(fallbackMethod = "findAllFallback", commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "60000"))
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products")
	public List<Product> findAll(@RequestHeader final Map<String, String> headers) {

		try {

			log.info(() -> "Product.findAll()");

			final List<Product> products = productCatalogClient.findAll(headers);

			final Cache cache = cacheManager.getCache("products_composite_cache");

			if (null != cache) {
				log.info(() -> "Adding produts into cache " + products);
				cache.put("findAll", products);
			}

			log.info(() -> "Returning products from findAll " + products);

			return products;

		} catch (final Exception e) {
			log.error(() -> "Exception in findAll", e);
			throw e;
		}

	}

	public List<Product> findAllFallback(@RequestHeader final Map<String, String> headers) {
		return productCatalogClientFallback.findAll(headers);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/{name}")
	public Product findByName(@PathVariable("name") final String name,
			@RequestHeader final Map<String, String> headers) {
		log.info(String.format("Product.findByName(%s)", name));
		return productCatalogClient.findByName(name, headers);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/products/{category}/{name}")
	public Product findByCategoryAndName(@PathVariable("category") final String category,
			@PathVariable("name") final String name, @RequestHeader final Map<String, String> headers) {
		log.info(String.format("Product.findByCategoryAndName(%s, %s)", name, category));
		return productCatalogClient.findByCategoryAndName(category, name, headers);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/api/v1/products")
	public Product add(@RequestBody final Product product, @RequestHeader final Map<String, String> headers) {
		log.info(String.format("Product.add(%s)", product));
		return productCatalogClient.add(product, headers);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/v1/products/{name}")
	public Product update(@PathVariable("name") final String name, @RequestBody final Product product,
			@RequestHeader final Map<String, String> headers) {
		log.info(String.format("Product.update(%s, %s)", name, product));
		return productCatalogClient.update(name, product, headers);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/api/v1/products/{name}")
	public void delete(@PathVariable("name") final String name, @RequestHeader final Map<String, String> headers) {
		log.info(String.format("Product.delete(%s)", name));
		productCatalogClient.delete(name, headers);
	}

}
