package com.seleccion.seleccion.service.impl;

import com.seleccion.seleccion.model.Category;
import com.seleccion.seleccion.model.Product;
import com.seleccion.seleccion.model.SearchPagination;
import com.seleccion.seleccion.repository.CategoryRepository;
import com.seleccion.seleccion.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl  implements CategoryService {

    public final static Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Optional<Category> getCategoryById(Long id){
        return categoryRepository.findById(id);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Page<Category> getCategoriesPage(SearchPagination searchPagination) {
        String[] parts = searchPagination.getSortBy().split(",");
        String sortBy = parts[0];
        String sortDir = parts[1];

        log.info("SearchPagination = ", searchPagination.toString());

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(searchPagination.getPage(), searchPagination.getSize(), sort);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categories;
    }

    @Override
    public List<Category> getCategoriesList() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean deleteCategory(Long id){
        try {
            categoryRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
