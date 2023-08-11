package com.yja.myapp.shoppingMall;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // productName에 포함된 문자열을 가진 제품들을 페이징된 형태로 반환하는 쿼리가 실행
    Page<Product> findByProductNameContaining(String productName, Pageable page);

}
