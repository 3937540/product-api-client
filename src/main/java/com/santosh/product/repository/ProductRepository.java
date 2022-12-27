package com.santosh.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santosh.product.entity.ProductDetail;


@Repository
public interface ProductRepository extends JpaRepository<ProductDetail, Long>{

}
