package com.example.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductDAO productDAO;

    @GetMapping("/products")
    public Map<String, Object> selectAllProduct() {
        Map<String, Object> rtnObj = new HashMap<>();

        List<ProductEntity> productList = productDAO.selectAllProduct();

        rtnObj.put("products", productList);
        return rtnObj;
    }

    @GetMapping("/products/{id}")
    public Map<String, Object> selectProduct(@PathVariable int id) {
        Map<String, Object> rtnObj = new HashMap<>();

        ProductEntity product = productDAO.selectProduct(id);

        rtnObj.put("product", product);
        return rtnObj;
    }

    @PostMapping("/products")
    public Map<String, Object> insertProduct(@RequestBody InsertProductRequest request) {
        Map<String, Object> rtnObj = new HashMap<>();

        productDAO.insertProduct(request.title, request.description);

        rtnObj.put("result", "success");
        return rtnObj;
    }

    @DeleteMapping("/products/{id}")
    public Map<String, Object> deleteProduct(@PathVariable int id) {
        Map<String, Object> rtnObj = new HashMap<>();

        productDAO.deleteProduct(id);

        rtnObj.put("result", "success");
        return rtnObj;
    }
}
