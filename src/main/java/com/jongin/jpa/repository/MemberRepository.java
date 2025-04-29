package com.jongin.jpa.repository;

import com.jongin.jpa.dto.MemberDto;
import com.jongin.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    //쿼리 메서드
    //findBy뒤에 컬러명 붙이면 그 컬럼을 기준으로 조회, findByUserIDAndUserPW 도 가능
    //ex) SELECT * FROM entity_member WHERE userID=? AND userPW=?
    //List<Member> findByUserNameLike(String username);
    //SELECT * FROM entity_member WHERE userName = '%?%'

    Optional<Member> findByUserID(String userID);
    Optional<Member> findByUserEmail(String userEmail);
    Optional<Member> findByUserIDAndUserEmail(String userID, String userEmail);

    @Query("select m from Member m where m.userName =: userName")
    List<Member> findByCustomMember(@Param("userName") String userName);

    @Query(value = "select m from entity_member m where m.userName =: userName", nativeQuery = true)
    List<Member> findByCustomNativeMember(@Param("userName") String userName);

}
