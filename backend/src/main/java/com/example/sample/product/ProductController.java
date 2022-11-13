package com.example.sample.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public Map<String, Object> selectAllProduct(@RequestParam(defaultValue = "id") String sortColumn,
                                                @RequestParam(defaultValue = "ASC") String sortType,
                                                @RequestParam(defaultValue = "1") int pageNum,
                                                @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> rtnObj = new HashMap<>();

        List<ProductEntity> productList = productService.selectProductList(sortColumn, sortType, pageNum, size);

        rtnObj.put("products", productList);
        return rtnObj;
    }

    @GetMapping("/products/{id}")
    public Map<String, Object> selectProduct(@PathVariable int id) {
        Map<String, Object> rtnObj = new HashMap<>();

        ProductEntity product = productService.selectProduct(id);

        rtnObj.put("product", product);
        return rtnObj;
    }

    @PostMapping("/products")
    public Map<String, Object> insertProduct(@RequestBody InsertProductRequest request) {
        Map<String, Object> rtnObj = new HashMap<>();

        productService.insertProduct(request);

        rtnObj.put("result", "success");
        return rtnObj;
    }

    @PutMapping("/products/{id}")
    public Map<String, Object> updateProduct(@PathVariable int id, @RequestBody UpdateProductRequest request) {
        Map<String, Object> rtnObj = new HashMap<>();

        productService.updateProduct(id, request);

        rtnObj.put("result", "success");
        return rtnObj;
    }

    @DeleteMapping("/products/{id}")
    public Map<String, Object> deleteProduct(@PathVariable int id) {
        Map<String, Object> rtnObj = new HashMap<>();

        productService.deleteProduct(id);

        rtnObj.put("result", "success");
        return rtnObj;
    }
}
