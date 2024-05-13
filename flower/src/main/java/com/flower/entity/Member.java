package com.flower.entity;

import com.flower.constant.MemberRole;
import com.flower.dto.MemberJoinDto;
import com.flower.dto.MemberUpdateDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.*;

@Builder
@Entity
@Table(name="member")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @Column(name="member_mno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    private String mid;

    private String mpw;

    private String mname;

    private String mphone;

    @Column(unique = true)  // 유니크 처리
    private String memail;  // 회원 검색 처리용

    private String mbirth;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;  // constant.MemberRole 사용자, 관리자 구분용

    public static Member createMember(MemberJoinDto memberJoinDto, PasswordEncoder passwordEncoder){
        Member member = new Member();

        member.setMid(memberJoinDto.getMid());

        String password = passwordEncoder.encode(memberJoinDto.getMpw());  // 패스워드 암호화 처리
        member.setMpw(password);
        member.setMname(memberJoinDto.getMname());
        member.setMphone(memberJoinDto.getMphone());
        member.setMemail(memberJoinDto.getMemail());
        member.setMbirth(memberJoinDto.getMbirth());
        member.setMemberRole(MemberRole.ADMIN);  //권한은 ADMIN

        return member;
    } // 회원 생성용 메서드 (dto와 암호화를 받아 Member 객체 리턴)

}
