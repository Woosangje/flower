package com.flower.repository;

import com.flower.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByMemail(String memail);
    // 이메일을 받아서 회원 정보를 가져옴.

}