package march4.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInCheck extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(LogInCheck.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	
		log.debug("enter interceptor -------------------------------");
		log.debug("session : {}", request.getSession().getAttribute("email"));

		if (request.getSession().getAttribute("email") == null) {
			if (request.getHeader("accept").contains("application/json")) {
				response.setStatus(401);
				log.debug("json");
				return false;
			}
			response.sendRedirect("/");
			log.debug("http");
			return false;
		}
		return true;
	}
}
// 1. preHandle - controller 이벤트 호출전
// 2. postHandle - controller 호출 후 view 페이지 출력전
// 3. afterCompletion - controller + view 페이지 모두 출력 후
// 리턴값이 true가 아닐경우 해당 컨트롤러로 이어주지 않는다.