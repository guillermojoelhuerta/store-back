package com.seleccion.seleccion.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seleccion.seleccion.exception.DeleteProductException;
import com.seleccion.seleccion.model.Category;
import com.seleccion.seleccion.model.ImageProduct;
import com.seleccion.seleccion.model.Product;
import com.seleccion.seleccion.model.SearchPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> getProductById(Long id);
    Page<Product> getProductsPage(SearchPagination searchPagination);
    List<Product> getProductsList();
    Product createProduct(MultipartFile images, String product, String categories) throws Exception;
    Product updateProduct(MultipartFile images, String product, String categories, String imageProduct) throws Exception;
    boolean deleteProduct(ImageProduct imageProduct) throws DeleteProductException;
}
