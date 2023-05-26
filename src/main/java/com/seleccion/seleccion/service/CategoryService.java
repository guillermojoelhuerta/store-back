package com.seleccion.seleccion.service;

import com.seleccion.seleccion.model.Category;
import com.seleccion.seleccion.model.SearchPagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Optional<Category> getCategoryById(Long id);
    Category saveCategory(Category category);
    Page<Category> getCategoriesPage(SearchPagination searchPagination);
    List<Category> getCategoriesList();

    boolean deleteCategory(Long id);
}
