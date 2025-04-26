package com.example.DoAnJava.Repository;

import com.example.DoAnJava.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByCategoryId(Long categoryId);
    List<Product> findAllByOrderByIdDesc();
    List<Product> findAllByOrderByCategoryAsc();
    List<Product> findAllByOrderBySalesCountDesc();
    List<Product> findByNameContainingIgnoreCase(String name);


}



