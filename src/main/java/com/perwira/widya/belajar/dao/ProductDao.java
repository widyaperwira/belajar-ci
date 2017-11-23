package com.perwira.widya.belajar.dao;

import com.perwira.widya.belajar.model.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by widyaperwira on 16-Nov-17.
 */
@Repository
public interface ProductDao extends PagingAndSortingRepository<Product, String> { }
