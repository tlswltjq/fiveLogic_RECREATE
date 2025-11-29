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
    private Email email;
    private Bio bio;

    private Member(MemberId id, UserId userId, UserPassword password, Name name, Nickname nickname, MemberType memberType, Boolean isActivated, Email email, Bio bio) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.memberType = memberType;
        this.isActivated = isActivated;
        this.email = email;
        this.bio = bio;
    }

    public static Member create(String userId, String password, String email, String firstName, String lastName, String nickname, MemberType memberType, String bio) {
        UserId createdUserId = new UserId(userId);
        UserPassword createdUserPassword = new UserPassword(password);
        Email createdEmail = new Email(email);
        Name createdName = new Name(firstName, lastName);
        Nickname createdNickname = new Nickname(nickname);
        Bio createdBio = new Bio(bio);
        return new Member(null, createdUserId, createdUserPassword, createdName, createdNickname, memberType, true, createdEmail, createdBio);
    }

    public static Member create(String userId, String password, String email, String firstName, String lastName, String nickname, String bio) {
        return create(userId, password, email, firstName, lastName, nickname, MemberType.MENTEE, bio);
    }


    public void delete() {
        this.isActivated = false;
    }

    public void updatePassword(String newPassword) {
        this.password = new UserPassword(newPassword);
    }

    public void updateNickname(String newNickname) {
        this.nickname = new Nickname(newNickname);
    }

    public void updateMemberType(MemberType newMemberType) {
        this.memberType = newMemberType;
    }

    public void updateBio(String newBio) {
        this.bio = new Bio(newBio);
    }

    public void updateEmail(String newEmail) {
        this.email = new Email(newEmail);
    }

    public void updateName( String firstName, String lastName) {
        this.name = new Name(firstName, lastName);
    }
}
