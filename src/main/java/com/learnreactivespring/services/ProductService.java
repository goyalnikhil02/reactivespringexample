package com.learnreactivespring.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.learnreactivespring.dao.ProductDao;
import com.learnreactivespring.model.Product;

@Service
@CacheConfig(cacheNames = "product")
public class ProductService {

	@Autowired
	private ProductDao productDao;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

	@CacheEvict(allEntries = true)
	public void clearCache() {
	}

	// This "product" is delcares in hazelcast.xml
	@Cacheable(cacheNames = "product")
	public Optional<Product> getProduct(String id)  {
		LOGGER.info("getProduct Executing for id {}", id);
		Optional<Product> product = productDao.findById(id);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return product;
	}

	@CachePut(value = "product", key = "#root.args[0].id")
	public Product updateProduct(Product product) {
		LOGGER.info("updateProduct Executing for id {}", product.getId());
		Product updatedProduct = productDao.save(product);
		return updatedProduct;
	}

	public void deleteAll() {
		LOGGER.info("deleteAllProducts Excuting {}");
		productDao.deleteAll();

	}

	@CacheEvict(value = "product", key = "#id")
	public void delete(String id) {
		//LOGGER.info("deleteProducts Excuting for id {}", prod.getName());
		//productDao.delete(prod);
		
		productDao.deleteById(id);

	}

	public void save(Product product) {
		LOGGER.info("save product Excuting for name {}", product.getName());
		productDao.save(product);

	}
}
