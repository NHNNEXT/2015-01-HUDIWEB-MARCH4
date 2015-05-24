package march4.controller;

import java.util.ArrayList;
import java.util.List;

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
@RequestMapping(value = "/users", headers = { "content-type=application/json" })
public class UserController {
	private static final Logger log = LoggerFactory
			.getLogger(FrontpageController.class);

	@Autowired
	UserDao userDao;

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody List<String> createUser(@RequestBody @Valid User user, BindingResult result) {
		log.debug(user.toString());
		
		List<ObjectError> errors = result.getAllErrors();
		if (errors.isEmpty()) {
			userDao.signup(user);
			log.debug("회원가입 완료!! : {}", user.toString());
			return null;
		}

		List<String> messages = new ArrayList<String>();
		for (ObjectError error : errors) {
			log.debug("error : {}", error.getDefaultMessage());
			messages.add(error.getDefaultMessage());
		}
		return messages;
	}

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public @ResponseBody String signIn(@RequestBody User user,
			HttpSession session) {

		log.debug(user.toString());
		log.debug(session.toString());
		session.removeAttribute("email");
		log.debug("check wheter email session is removed" +session.toString());
		
		if (!userDao.loginSuccess(user)) {
			log.debug("로그인 실패");
			if (userDao.existEmail(user)) {
				return StringToArray.convert("패스워드가 틀렸습니다.");
			} else {
				return StringToArray.convert("존재하지 않는 이메일 입니다.");
			}
		}
		log.debug("로그인 성공!!");
		session.setAttribute("email", user.getEmail());
		return "true";
	}

	@RequestMapping(value = "/signout", method = RequestMethod.POST)
	public String signOut(HttpSession session) {
		String email = (String) session.getAttribute("email");
		session.removeAttribute("email");
		log.debug("{} 로그아웃", email);
		return "index";
	}
}