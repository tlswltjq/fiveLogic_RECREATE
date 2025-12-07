package com.fivelogic_recreate.fixture.member;

import com.fivelogic_recreate.member.domain.*;

public class MemberFixture {
    private Long memberId = 1L;
    private String userId = "user1";
    private String password = "password";
    private String email = "email@test.com";
    private String firstname = "John";
    private String lastname = "Doe";
    private String nickname = "johnd";
    private MemberType memberType = MemberType.MENTEE;
    private String bio = "Hello bio";

    public MemberFixture withMemberId(Long memberId) {
        this.memberId = memberId;
        return this;
    }

    public MemberFixture withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public MemberFixture withPassword(String password) {
        this.password = password;
        return this;
    }

    public MemberFixture withEmail(String email) {
        this.email = email;
        return this;
    }

    public MemberFixture withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public MemberFixture withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public MemberFixture withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public MemberFixture withMemberType(MemberType memberType) {
        this.memberType = memberType;
        return this;
    }

    public MemberFixture withBio(String bio) {
        this.bio = bio;
        return this;
    }

    public Member build() {
        return Member.reconstitute(
                new MemberId(memberId),
                new UserId(userId),
                new UserPassword(password),
                new Name(firstname, lastname),
                new Nickname(nickname),
                memberType,
                true,
                new Email(email),
                new Bio(bio)
        );
    }
}
