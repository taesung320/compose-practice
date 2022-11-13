package com.example.sample.product;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDAO {

    List<ProductEntity> selectProductList(String sortColumn, String sortType, int skip, int size);

    ProductEntity selectProduct(int id);

    void insertProduct(String title, String description);

    void updateProduct(int id, String title, String description);

    void deleteProduct(int id);
}
