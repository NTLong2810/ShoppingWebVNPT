package com.example.shoppingweb.service;

import com.example.shoppingweb.model.*;
import com.example.shoppingweb.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;

    public ProductService(ProductRepository productRepository,
                          SupplierRepository supplierRepository,
                          BrandRepository brandRepository,
                          CategoryRepository categoryRepository,
                          SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }
    public Product getProductByName(String name) {
        return productRepository.findByName(name);
    }
    public Product getProductById(Long productId){
        return productRepository.getProductById(productId);
    }
}
