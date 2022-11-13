package com.example.sample.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    @Override
    public List<ProductEntity> selectProductList(String sortColumn, String sortType, int pageNum, int size) {
        return productDAO.selectProductList(sortColumn, sortType,(pageNum - 1) * size, size);
    }

    @Override
    public ProductEntity selectProduct(int id) {
        return productDAO.selectProduct(id);
    }

    @Override
    public void insertProduct(InsertProductRequest request) {
        productDAO.insertProduct(request.title, request.description);
    }

    @Override
    public void updateProduct(int id, UpdateProductRequest request) {
        productDAO.updateProduct(id, request.title, request.description);
    }

    @Override
    public void deleteProduct(int id) {
        productDAO.deleteProduct(id);
    }
}
