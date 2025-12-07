package com.fivelogic_recreate.member.infrastructure.persistence;

import com.fivelogic_recreate.member.domain.*;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String userId;
    private String password;
    private String name;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private MemberType memberType;
    private Boolean isActivated;
    private String email;
    @Column(columnDefinition = "TEXT")
    private String bio;

    @Builder(access = AccessLevel.PRIVATE)
    private MemberJpaEntity(Long id, String userId, String password, String name,
                            String nickname, MemberType memberType, Boolean isActivated,
                            String email, String bio) {
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

    public static MemberJpaEntity from(Member member) {
        Long idValue = (member.getId() != null) ? member.getId().value() : null;

        return new MemberJpaEntity(
                idValue,
                member.getUserId().value(),
                member.getPassword().value(),
                member.getName().value(),
                member.getNickname().value(),
                member.getMemberType(),
                member.getIsActivated(),
                member.getEmail().value(),
                member.getBio().value()
        );
    }


    public Member toDomain() {
        String[] nameParts = this.name != null ? this.name.split(" ", 2) : new String[]{"", ""};
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        return Member.reconstitute(new MemberId(this.id), new UserId(this.userId), new UserPassword(this.password),
                                   new Name(firstName, lastName), new Nickname(this.nickname), this.memberType,
                                   this.isActivated, new Email(this.email), new Bio(this.bio));
    }
}