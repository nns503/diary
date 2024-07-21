package backend.diary.global.auth.controller;

import backend.diary.global.auth.service.JoinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JoinControllerTest {

    @InjectMocks
    private JoinController joinController;

    @Mock
    private JoinService joinService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void 회원가입_성공(){

    }

    @Test
    void 회원가입_실패_중복아이디(){

    }

    @Test
    void 회원가입_실패_중복닉네임(){

    }


}