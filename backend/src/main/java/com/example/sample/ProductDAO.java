package com.example.sample;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDAO {

    public List<ProductEntity> selectAllProduct();

    public ProductEntity selectProduct(int id);

    public void insertProduct(String title, String description);

    public void deleteProduct(int id);
}
