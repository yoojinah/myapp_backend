//package com.yja.myapp.shoppingMall;
//
//import com.yja.myapp.auth.Auth;
//import com.yja.myapp.auth.AuthProfile;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping(value = "/cart")
//public class CartController {
//    @Autowired
//    ProductRepository repo;
//
//
//    @PostMapping("/add")
//    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody Product product) {
//        if (product.getProductName() == null || product.getProductName().isEmpty()
//                || Double.isNaN(product.getProductPrice()) ||
//                product.getProductColor() == null || product.getProductColor().isEmpty()) {
//            // 필수 필드가 비어있는 경우에 대한 예외 처리
//            return ResponseEntity.badRequest().build();
//        }
//        Product existingProduct = repo.findByProductName(product.getProductName());
//
//        if (existingProduct != null) {
//            // 이미 장바구니에 있는 상품인 경우 수량 증가 등의 업데이트 작업 수행
//            existingProduct.setQuantity(existingProduct.getQuantity() + 1);
//            repo.save(existingProduct);
//        } else {
//            // 새로운 상품인 경우 장바구니에 추가
//            product.setQuantity(1);
//            repo.save(product);
//        }
//
//        return ResponseEntity.ok().build();
//    }
//
//    @GetMapping
//    public List<Product> getCartList() {
//        List<Product> CartproductList = repo.findAll(Sort.by(Sort.Direction.DESC, "no"));
//
//        return CartproductList;
//    }
//
//
//
//
//
//}
