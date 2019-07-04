package com.learnreactivespring.dao;


import org.springframework.data.repository.CrudRepository;

import com.learnreactivespring.model.Product;

public interface ProductDao extends CrudRepository<Product, String> {
}
