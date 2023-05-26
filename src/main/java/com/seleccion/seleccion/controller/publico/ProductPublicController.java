package com.seleccion.seleccion.controller.publico;

import com.seleccion.seleccion.model.Category;
import com.seleccion.seleccion.model.Product;
import com.seleccion.seleccion.model.SearchPagination;
import com.seleccion.seleccion.service.CategoryService;
import com.seleccion.seleccion.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/product_p")
public class ProductPublicController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @PostMapping(value = "get-products-page", produces = "application/json; charset=utf-8")
    public Page<Product> getProductsPage(@RequestBody SearchPagination searchPagination) {
        return productService.getProductsPage(searchPagination);
    }

    @GetMapping(value = "get-categories-list", produces = "application/json; charset=utf-8")
    public List<Category> getCategoriesList() {
        return categoryService.getCategoriesList();
    }

    @GetMapping(value = "get-product-by-id/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Long id){ return productService.getProductById(id);}
                                        
}
