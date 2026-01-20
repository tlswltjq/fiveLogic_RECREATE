package com.fivelogic_recreate.member.application.query;

import com.fivelogic_recreate.member.application.MemberReader;
import com.fivelogic_recreate.member.application.command.dto.GetMyProfileCommand;
import com.fivelogic_recreate.member.application.command.dto.GetMyProfileResult;
import com.fivelogic_recreate.member.application.query.dto.MyProfile;
import com.fivelogic_recreate.member.domain.model.MemberType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MyInfoServiceTest {

    @InjectMocks
    private MyInfoService myInfoService;

    @Mock
    private MemberReader memberReader;

    @Test
    @DisplayName("내 프로필 정보를 조회한다.")
    void getProfile() {
        // given
        String userId = "user1";
        GetMyProfileCommand command = new GetMyProfileCommand(userId);
        MyProfile myProfile = new MyProfile(
                "Nickname",
                "test@test.com",
                "bio");

        given(memberReader.getMemberProfile(userId)).willReturn(myProfile);

        // when
        GetMyProfileResult result = myInfoService.getProfile(command);

        // then
        assertThat(result.nickname()).isEqualTo(myProfile.nickname());
        assertThat(result.email()).isEqualTo(myProfile.email());
        assertThat(result.bio()).isEqualTo(myProfile.bio());
    }
}
