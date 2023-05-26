package com.seleccion.seleccion.controller;

import com.seleccion.seleccion.model.ImageProduct;
import com.seleccion.seleccion.model.Product;
import com.seleccion.seleccion.model.SearchPagination;
import com.seleccion.seleccion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="product")
public class ProductController {

    @Autowired
    ProductService productService;

    private Validator validator;

    public ProductController(Validator validator) {
        this.validator = validator;
    }

    @GetMapping(value = "get-product-by-id/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Long id){ return productService.getProductById(id);}

    @GetMapping(value = "get-products-list", produces = "application/json; charset=utf-8")
    public List<Product> getProductsList() {
        return productService.getProductsList();
    }

    @PostMapping(value = "get-products-page", produces = "application/json; charset=utf-8")
    public Page<Product> getProductsPage(@RequestBody SearchPagination searchPagination) {
        return productService.getProductsPage(searchPagination);
    }

    @PostMapping(value = "create-product", produces = "application/json; charset=utf-8")
    public Product createProduct(@RequestParam("images") MultipartFile images,
                                 @RequestParam("product") String newProduct,
                                 @RequestParam("categories") String categories
    ) throws Exception {
        return productService.createProduct(images, newProduct, categories);
    }

    @PutMapping(value="update-product")
    public Product updateProduct(@RequestParam(value = "images",  required = false) MultipartFile images,
                                 @RequestParam("product") String product,
                                 @RequestParam("categories") String categories,
                                 @RequestParam("imageProduct") String imageProduct
    ) throws Exception {
        return productService.updateProduct(images, product, categories, imageProduct);
    }

    @PostMapping(value="delete-product")
    public boolean deleteProduct(@RequestBody ImageProduct imageProduct){
        return productService.deleteProduct(imageProduct);
    }
}
