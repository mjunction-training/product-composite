package com.training.mjunction.product.composite.clients;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.training.mjunction.product.composite.domain.Product;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ProductCatalogClientFallback implements ProductCatalogClient {

	@Autowired
	private CacheManager cacheManager;

	@Override
	public List<Product> findAll(final Map<String, String> headers) {

		log.info(() -> "Product.findAllFallback()");

		final Cache cache = cacheManager.getCache("products_composite_cache");

		if (null == cache) {
			log.info(() -> "No cache found with name : products_composite_cache");
			return Collections.emptyList();
		}

		final ValueWrapper wrapper = cache.get("findAll");

		if (null == wrapper || null == wrapper.get()) {
			log.info(() -> "No cache value found with name : products_composite_cache::findAll");
			return Collections.emptyList();
		}

		@SuppressWarnings("unchecked")
		final List<Product> products = (List<Product>) wrapper.get();

		log.info(() -> "Returning products from findAllFallback " + products);

		return products;
	}

	@Override
	public Product findByName(final String name, final Map<String, String> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product findByCategoryAndName(final String category, final String name, final Map<String, String> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product add(final Product product, final Map<String, String> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product update(final String name, final Product product, final Map<String, String> headers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(final String name, final Map<String, String> headers) {
		// TODO Auto-generated method stub

	}

}
