package com.fivelogic_recreate.member.domain;

import lombok.Getter;

@Getter
public class Member {
    private final MemberId id;
    private final UserId userId;
    private UserPassword password;
    private Name name;
    private Nickname nickname;
    private MemberType memberType;
    private Boolean isActivated;

    private Member(MemberId id, UserId userId, UserPassword password, Name name, Nickname nickname, MemberType memberType, Boolean isActivated) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.memberType = memberType;
        this.isActivated = isActivated;
    }

    public static Member create(String userId, String password, String firstName, String lastName, String nickname, MemberType memberType) {
        UserId createdUserId = new UserId(userId);
        UserPassword createdUserPassword = new UserPassword(password);
        Nickname createdNickname = new Nickname(nickname);
        Name createdName = new Name(firstName, lastName);
        return new Member(null, createdUserId, createdUserPassword, createdName, createdNickname, memberType, true);
    }

    public static Member create(String userId, String password,  String firstName, String lastName, String nickname) {
        return create(userId, password, firstName, lastName, nickname, MemberType.MENTEE);
    }

    public void delete() {
        this.isActivated = false;
    }

    public void changePassword(String newPassword) {
        this.password = new UserPassword(newPassword);
    }

    public void changeNickname(String newNickname) {
        this.nickname = new Nickname(newNickname);
    }

    public void changeMemberType(MemberType newMemberType) {
        this.memberType = newMemberType;
    }
}
