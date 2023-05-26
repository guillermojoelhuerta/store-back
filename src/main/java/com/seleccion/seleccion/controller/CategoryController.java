package com.seleccion.seleccion.controller;

import com.seleccion.seleccion.exception.BindingResultException;
import com.seleccion.seleccion.model.Category;
import com.seleccion.seleccion.model.SearchPagination;
import com.seleccion.seleccion.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="category")
public class CategoryController {
    public final static Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "get-category-by-id/{id}", produces = "application/json; charset=utf-8")
    public Optional<Category> getCategoryById(@PathVariable("id") Long id){
        return categoryService.getCategoryById(id);
    }

    @PostMapping(value = "get-categories-page", produces = "application/json; charset=utf-8")
    public Page<Category> getCategoriesPage(@RequestBody SearchPagination searchPagination) {
        return categoryService.getCategoriesPage(searchPagination);
    }

    @GetMapping(value = "get-categories-list", produces = "application/json; charset=utf-8")
    public List<Category> getCategoriesList() {
        return categoryService.getCategoriesList();
    }

    @PostMapping(value = "save-category", produces = "application/json; charset=utf-8")
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody Category category, BindingResult result) {
        if(result.hasErrors()){
            throw new BindingResultException(result);
        }
        Category cat = categoryService.saveCategory(category);
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    @PutMapping(value = "update-category", produces = "application/json; charset=utf-8")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category, BindingResult result) {
        if(result.hasErrors()){
            throw new BindingResultException(result);
        }
        Category cat = categoryService.saveCategory(category);
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete-category-by-id/{id}", produces = "application/json; charset=utf-8")
    public ResponseEntity deleteCategory(@PathVariable("id") Long id){
        boolean res = categoryService.deleteCategory(id);
        if (res) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
