package com.seleccion.seleccion.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seleccion.seleccion.exception.*;
import com.seleccion.seleccion.model.Category;
import com.seleccion.seleccion.model.ImageProduct;
import com.seleccion.seleccion.model.Product;
import com.seleccion.seleccion.model.SearchPagination;
import com.seleccion.seleccion.repository.ImageProductRepository;
import com.seleccion.seleccion.repository.ProductRepository;
import com.seleccion.seleccion.service.FileService;
import com.seleccion.seleccion.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    public final static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    FileService fileService;

    @Autowired
    ImageProductRepository imageProductRepository;

    private Validator validator;

    public ProductServiceImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ResourceNotFoundException("Producto no encontrado.");
        }
        return product;
    }

    @Override
    public Page<Product> getProductsPage(SearchPagination searchPagination) {
        String[] parts = searchPagination.getSortBy().split(",");
        String sortBy = parts[0];
        String sortDir = parts[1];

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(searchPagination.getPage(), searchPagination.getSize(), sort);

        Page<Product> products = new PageImpl<>(new ArrayList<>());
        String msg = "";
        switch (searchPagination.getSearchBy()){
            case "category":
                products =  productRepository.findByCategoriesName(searchPagination.getSearch(), pageable);
                msg = "No existen productos con esa categoría.";
                break;
            case "name":
                products = productRepository.findByName(searchPagination.getSearch(), pageable);
                msg = "No existen productos con ese nombre.";
                break;
            case "price":
                products = productRepository.findByPrice(new BigDecimal(searchPagination.getSearch()), pageable);
                msg = "No existen productos con ese precio.";
                break;
            default:
                products = productRepository.findAll(pageable);
                msg = "No se encuentran productos.";
                break;
        }

        if (products.isEmpty()) {
            throw new ResourceNotFoundException(msg);
        }

        return products;
    }
    @Override
    public List<Product> getProductsList() {
        List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            throw new ResourceNotFoundException("No hay productos.");
        }
        return products;
    }

    @Override
    public Product createProduct(MultipartFile images, String newProduct, String categories) throws Exception {
        Product product = new ObjectMapper().readValue(newProduct, Product.class);
        BindingResult bindingResult = new DataBinder(product).getBindingResult();
        validator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        product.setActive(true);

        TypeReference<List<Category>> typeRef = new TypeReference<List<Category>>() {};
        List<Category> listCategories = new ObjectMapper().readValue(categories, typeRef);

        product.setCategories(listCategories);
        String nameImage = fileService.save(images);

        Product prod = productRepository.save(product);
        ImageProduct imageProduct = new ImageProduct();
        imageProduct.setName(nameImage);
        imageProduct.setIdUsuario(prod.getIdUsuario());
        imageProduct.setIdProduct(prod.getIdProduct());
        imageProductRepository.save(imageProduct);

        return prod;
    }

    @Override
    public Product updateProduct(MultipartFile images, String newProduct, String categories, String imageProduct) throws Exception {
        Product product = new ObjectMapper().readValue(newProduct, Product.class);
        BindingResult bindingResult = new DataBinder(product).getBindingResult();
        validator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindingResultException(bindingResult);
        }
        product.setActive(true);

        TypeReference<List<Category>> typeRef = new TypeReference<List<Category>>() {};
        List<Category> listCategories = new ObjectMapper().readValue(categories, typeRef);

        product.setCategories(listCategories);
        Product prod = productRepository.save(product);

        if(images != null) {
            ImageProduct imgProduct = new ObjectMapper().readValue(imageProduct, ImageProduct.class);
            fileService.deleteArchivo(imgProduct.getName());
            String nameImage = fileService.save(images);
            ImageProduct updateImageProduct = new ImageProduct();
            updateImageProduct.setIdImageProduct(imgProduct.getIdImageProduct());
            updateImageProduct.setName(nameImage);
            updateImageProduct.setIdUsuario(prod.getIdUsuario());
            updateImageProduct.setIdProduct(prod.getIdProduct());
            imageProductRepository.save(updateImageProduct);
            prod.setImageProduct(updateImageProduct);
        }

        return prod;
    }

    @Override
    public boolean deleteProduct(ImageProduct imageProduct) {
        try {
            fileService.deleteArchivo(imageProduct.getName());
            imageProductRepository.deleteById(imageProduct.getIdImageProduct());
            productRepository.deleteById(imageProduct.getIdProduct());
            return true;
        }catch (DeleteFileException e){
            throw new DeleteProductException(e.getMessage());
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
