package hello.login.web.session;


import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        // 세션 생성
        MockHttpServletResponse response = new MockHttpServletResponse();
        Member member = new Member();
        sessionManager.createSession(member, response);

        // 요청에 응답 쿠키 저장(브라우저에서 하는 행위)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); // mySessionId=1293095u0dslkf

        // 세션 조회
        Object session = sessionManager.getSession(request);
        assertThat(session).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expireSession = sessionManager.getSession(request);
        assertThat(expireSession).isNull();
    }
}