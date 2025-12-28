package com.fivelogic_recreate.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id", unique = true, nullable = false))
    private UserId userId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password", nullable = false))
    private UserPassword password;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name", nullable = false)),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name", nullable = false))
    })
    private Name name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "nickname", nullable = false))
    private Nickname nickname;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private Boolean isActivated;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", unique = true, nullable = false))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "bio", columnDefinition = "TEXT"))
    private Bio bio;

    private Member(Long id, UserId userId, UserPassword password, Name name, Nickname nickname, MemberType memberType,
            Boolean isActivated, Email email, Bio bio) {
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

    public static Member reconstitute(Long id, UserId userId, UserPassword password, Name name,
            Nickname nickname, MemberType memberType, Boolean isActivated,
            Email email, Bio bio) {
        return new Member(id, userId, password, name, nickname, memberType, isActivated, email, bio);
    }

    public static Member join(String userId, String password, String email, String firstName, String lastName,
            String nickname, String bio) {
        return join(userId, password, email, firstName, lastName, nickname, MemberType.MENTEE, bio);
    }

    public static Member join(String userId, String password, String email, String firstName, String lastName,
            String nickname, MemberType memberType, String bio) {
        return new Member(
                null,
                new UserId(userId),
                new UserPassword(password),
                new Name(firstName, lastName),
                new Nickname(nickname),
                memberType,
                true,
                new Email(email),
                new Bio(bio));
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

    public void updateName(String firstName, String lastName) {
        this.name = new Name(firstName, lastName);
    }

    public boolean checkPassword(String rawPassword) {
        return this.password.match(rawPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isSameUser(String targetUserId) {
        return this.userId.value().equals(targetUserId);
    }
}
