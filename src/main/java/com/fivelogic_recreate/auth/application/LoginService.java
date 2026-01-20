//package com.fivelogic_recreate.auth.application;
//
//import com.fivelogic_recreate.auth.application.dto.LoginCommand;
//import com.fivelogic_recreate.auth.application.dto.LoginResult;
//import com.fivelogic_recreate.member.domain.model.Member;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class LoginService {
//
//    public LoginResult login(LoginCommand command) {
//        String userId = command.userId();
//        String password = command.password();
//
//
//        return new LoginResult(member.getUserId().value(), "temp-token", "temp-refresh-token");
//    }
//}
