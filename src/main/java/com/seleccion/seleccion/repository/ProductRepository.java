package com.seleccion.seleccion.repository;

import com.seleccion.seleccion.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByName(String name, Pageable pageable);
    Page<Product> findByPrice(BigDecimal price, Pageable pageable);
    Page<Product> findByCategoriesName(String category, Pageable pageable);
}
