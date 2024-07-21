package backend.diary.global.auth.filter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class JWTFilterTest {

        /*
    기존 리프레쉬 토큰을 삭제하고,
    새로운 리프레쉬 토큰과 함께 Access 토큰을 발급함
     */
    @Test
    void AccessToken_인증_성공(){

    }

    /*
    리프레쉬 토큰이 존재하지 않음
     */
    @Test
    void AccessToken_Null(){

    }

    /*
    리프레쉬 토큰의 유효시간이 경과됨
     */
    @Test
    void AccessToken_유효시간_만료(){

    }

    /*
    리프레쉬 토큰의 카테고리가 access가 아님
     */
    @Test
    void AccessToken_카테고리_access_X(){

    }
}