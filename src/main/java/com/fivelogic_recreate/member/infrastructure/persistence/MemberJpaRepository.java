package com.fivelogic_recreate.member.infrastructure.persistence;

import com.fivelogic_recreate.member.application.query.dto.MemberDetail;
import com.fivelogic_recreate.member.application.query.dto.MyProfile;
import com.fivelogic_recreate.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUserId(String userId);

    Optional<Member> findByNickname(String nickname);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String value);

    @Query("SELECT m.id, m.userId.value, m.nickname.value, CAST(m.memberType as string), CONCAT(m.name.firstName, ' ', m.name.lastName), m.email.value, m.bio.value FROM Member m WHERE m.userId.value = :userId")
    Optional<MemberDetail> findUserDetailByUserId(@Param("userId") String userId);

    @Query("SELECT m.id, m.userId.value, m.nickname.value, CAST(m.memberType as string), CONCAT(m.name.firstName, ' ', m.name.lastName), m.email.value, m.bio.value FROM Member m WHERE m.memberType != 'ADMIN'")
    List<MemberDetail> findUserDetailsExceptAdmin();

    @Query("SELECT m.id, m.userId.value, m.nickname.value, CAST(m.memberType as string), CONCAT(m.name.firstName, ' ', m.name.lastName), m.email.value, m.bio.value FROM Member m WHERE m.memberType = :memberType")
    List<MemberDetail> findUserDetailsByType(@Param("memberType") String memberType);

    @Query("SELECT m.nickname.value, m.email.value, m.bio.value FROM Member m WHERE m.userId.value = :userId")
    Optional<MyProfile> findProfileByUserId(@Param("userId") String userId);
}
