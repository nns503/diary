package backend.diary.global.auth.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class ReissueControllerTest {

    /*
    기존 리프레쉬 토큰을 삭제하고,
    새로운 리프레쉬 토큰과 함께 Access 토큰을 발급함
     */
    @Test
    void RefreshToken_재발급_성공(){

    }

    /*
    리프레쉬 토큰이 존재하지 않음
     */
    @Test
    void RefreshToken_Null(){

    }

    /*
    리프레쉬 토큰의 유효시간이 경과됨
     */
    @Test
    void RefreshToken_유효시간_만료(){

    }

    /*
    리프레쉬 토큰의 카테고리가 refresh가 아님
     */
    @Test
    void RefreshToken_카테고리_refersh_X(){

    }

    /*
    리프레쉬 토큰가 저장 되어있지 않음
     */
    @Test
    void RefreshToken_Repository에_없음(){

    }
}