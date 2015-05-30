package march4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import march4.dao.UserDao;
import march4.model.User;
import march4.util.StringToArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value="/users", headers = { "Accept=application/json" })
public class UserController {
	private static final Logger log = LoggerFactory
			.getLogger(UserController.class);

	@Autowired
	UserDao userDao;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody List<String> createUser(@RequestBody @Valid User user,
			BindingResult result, HttpServletResponse resp) {

		log.debug(user.toString());
		
		List<ObjectError> errors = result.getAllErrors();
		if (errors.isEmpty()) {
			userDao.signup(user);
			log.debug("회원가입 완료!! : {}", user.toString());
			return null;
		}

		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		List<String> messages = new ArrayList<String>();
		for (ObjectError error : errors) {
			log.debug("error : {}", error.getDefaultMessage());
			resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			messages.add(error.getDefaultMessage());
		}
		return messages;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Object getCurrentUser(HttpSession session, HttpServletResponse resp) {
		
		log.debug("get current user info");
		log.debug(session.toString());
		List<String> errors = new ArrayList<String>();
		
		String email = (String) session.getAttribute("email");
		if(email == null){
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			log.debug("there is no session.");
			errors.add("세션이 만료되었습니다. 다시 로그인해주세요.");
			return errors;
		}

		User user = userDao.getUserByEmail(email);
		if(user == null){
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			signOut(session);
			log.debug("there is no user on DB that matches session user.");
			errors.add("잘못된 세션입니다. 로그아웃합니다.");
			return errors;
		}
		
		log.debug(user.toString());
		return user;
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public @ResponseBody String signIn(@RequestBody User user,
			HttpSession session, HttpServletResponse resp) {

		log.debug(user.toString());
		log.debug(session.toString());
		session.removeAttribute("email");
		log.debug("check wheter email session is removed" +session.toString());
		
		if (!userDao.loginSuccess(user)){ 
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			log.debug("로그인 실패");
			if (userDao.existEmail(user)) {
				return StringToArray.convert("패스워드가 틀렸습니다.");
			} else {
				return StringToArray.convert("존재하지 않는 이메일 입니다.");
			}
		}
	
		log.debug("로그인 성공!!");
		session.setAttribute("email", user.getEmail());
		return null;
	}

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/signout", method = RequestMethod.POST)
	public String signOut(HttpSession session) {
		String email = (String) session.getAttribute("email");
		session.removeAttribute("email");
		log.debug("{} 로그아웃", email);
		return null;
	}
}