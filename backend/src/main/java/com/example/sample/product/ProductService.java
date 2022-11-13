package com.example.sample.product;

import java.util.List;

public interface ProductService {
    List<ProductEntity> selectProductList(String sortColumn, String sortType, int pageNum, int size);

    ProductEntity selectProduct(int id);

    void insertProduct(InsertProductRequest request);

    void updateProduct(int id, UpdateProductRequest request);

    void deleteProduct(int id);

}
