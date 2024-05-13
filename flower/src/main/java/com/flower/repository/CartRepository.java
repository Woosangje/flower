package com.flower.repository;

import com.flower.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {

    Cart findByMemberMid(String memberMid);  // 332추가 현재 로그인한 회원이 Cart 엔티티를 찾음

}