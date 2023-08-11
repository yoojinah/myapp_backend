package com.yja.myapp.shoppingMall;



import com.yja.myapp.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value = "/shopping")
public class ShoppingController {
    Map<Long, Product> map = new ConcurrentHashMap<>();
    AtomicLong num = new AtomicLong(0);

    @Autowired
    ProductRepository repo;




    @GetMapping
    public List<Product> getProductList() {
        List<Product> productList = repo.findAll(Sort.by(Sort.Direction.DESC, "no"));

        return productList;
    }




    @PostMapping
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody Product product) {
        if (product.getProductName() == null || product.getProductName().isEmpty() || Double.isNaN(product.getProductPrice())) {
            Map<String, Object> res = new HashMap<>();
            res.put("data", null);
            res.put("message", "[productname], [productprice] is field required");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        }




        Product saveProduct = repo.save(product);


        Map<String, Object> res = new HashMap<>();
        res.put("data", saveProduct);
        res.put("message", "create");


        return ResponseEntity.ok().body(res);
    }
}
