package com.fivelogic_recreate.member.infrastructure.persistence;

import com.fivelogic_recreate.fixture.member.MemberFixture;
import com.fivelogic_recreate.member.domain.Member;
import com.fivelogic_recreate.member.domain.Nickname;
import com.fivelogic_recreate.member.domain.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberRepositoryAdapterTest {

    @Mock
    private MemberJpaRepository repository;

    @InjectMocks
    private MemberRepositoryAdapter adapter;

    private final MemberFixture memberFixture = new MemberFixture();


    @Test
    @DisplayName("존재하는 UserId로 조회 시 Member 도메인 객체를 반환한다.")
    void shouldReturnMemberDomainObjectWhenFindingByExistingUserId() {
        Member member = memberFixture.build();
        UserId userId = member.getUserId();
        MemberJpaEntity entity = MemberJpaEntity.from(member);
        when(repository.findByUserId(userId.value())).thenReturn(Optional.of(entity));

        Optional<Member> result = adapter.findByUserId(userId);

        assertThat(result).isPresent();
        assertThat(result.get().getUserId()).isEqualTo(userId);
        verify(repository).findByUserId(userId.value());
    }

    @Test
    @DisplayName("존재하지 않는 UserId로 조회 시 Optional.empty를 반환한다.")
    void shouldReturnEmptyOptionalWhenFindingByNonExistingUserId() {
        UserId userId = new UserId("notExistUser");
        when(repository.findByUserId(userId.value())).thenReturn(Optional.empty());

        Optional<Member> result = adapter.findByUserId(userId);

        assertThat(result).isEmpty();
        verify(repository).findByUserId(userId.value());
    }

    @Test
    @DisplayName("존재하는 Nickname으로 조회 시 Member 도메인 객체를 반환한다.")
    void shouldReturnMemberDomainObjectWhenFindingByExistingNickname() {
        Member member = memberFixture.build();
        Nickname nickname = member.getNickname();
        MemberJpaEntity entity = MemberJpaEntity.from(member);
        when(repository.findByNickname(nickname.value())).thenReturn(Optional.of(entity));

        Optional<Member> result = adapter.findByNickname(nickname);

        assertThat(result).isPresent();
        assertThat(result.get().getNickname()).isEqualTo(nickname);
        verify(repository).findByNickname(nickname.value());
    }

    @Test
    @DisplayName("존재하지 않는 Nickname으로 조회 시 Optional.empty를 반환한다.")
    void shouldReturnEmptyOptionalWhenFindingByNonExistingNickname() {
        Nickname nickname = new Nickname("non_existing_nickname");
        when(repository.findByNickname(nickname.value())).thenReturn(Optional.empty());

        Optional<Member> result = adapter.findByNickname(nickname);

        assertThat(result).isEmpty();
        verify(repository).findByNickname(nickname.value());
    }

    @Test
    @DisplayName("Member 객체를 저장하고 저장된 Member 객체를 반환한다.")
    void shouldSaveAndReturnMember() {
        Member memberToSave = memberFixture.build();
        MemberJpaEntity entity = MemberJpaEntity.from(memberToSave);
        when(repository.save(any(MemberJpaEntity.class))).thenReturn(entity);

        Member savedMember = adapter.save(memberToSave);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getId()).isEqualTo(memberToSave.getId());
        assertThat(savedMember.getUserId()).isEqualTo(memberToSave.getUserId());
        verify(repository).save(any(MemberJpaEntity.class));
    }

    @Test
    @DisplayName("존재하는 UserId의 존재 여부 확인 시 true를 반환한다.")
    void shouldReturnTrueWhenCheckingExistenceOfExistingUserId() {
        UserId userId = new UserId("existUser");
        when(repository.existsByUserId(userId.value())).thenReturn(true);

        boolean result = adapter.existsByUserId(userId);

        assertThat(result).isTrue();
        verify(repository).existsByUserId(userId.value());
    }

    @Test
    @DisplayName("존재하지 않는 UserId의 존재 여부 확인 시 false를 반환한다.")
    void shouldReturnFalseWhenCheckingExistenceOfNonExistingUserId() {
        UserId userId = new UserId("notExistUser");
        when(repository.existsByUserId(userId.value())).thenReturn(false);

        boolean result = adapter.existsByUserId(userId);

        assertThat(result).isFalse();
        verify(repository).existsByUserId(userId.value());
    }

    @Test
    @DisplayName("모든 멤버 조회 시 Member 도메인 객체 리스트를 반환한다.")
    void shouldReturnListOfMembersWhenFindAll() {
        Member member1 = memberFixture.withUserId("user1").build();
        Member member2 = memberFixture.withUserId("user2").build();
        List<MemberJpaEntity> entities = Arrays.asList(
                MemberJpaEntity.from(member1),
                MemberJpaEntity.from(member2)
        );
        when(repository.findAll()).thenReturn(entities);

        List<Member> resultList = adapter.findAll();

        assertThat(resultList).hasSize(2);
        assertThat(resultList.get(0).getUserId()).isEqualTo(member1.getUserId());
        assertThat(resultList.get(1).getUserId()).isEqualTo(member2.getUserId());
        verify(repository).findAll();
    }

    @Test
    @DisplayName("멤버가 없을 때 모든 멤버 조회 시 빈 리스트를 반환한다.")
    void shouldReturnEmptyListWhenNoMembersExist() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Member> resultList = adapter.findAll();

        assertThat(resultList).isEmpty();
        verify(repository).findAll();
    }
}