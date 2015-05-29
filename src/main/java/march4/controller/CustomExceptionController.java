package march4.controller;

import march4.exception.EmailDuplicationExeption;
import march4.util.StringToArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionController {
	//TODO class 명을 바꾸면 왜 작동이 안될까?
	private static final Logger log = LoggerFactory.getLogger(CustomExceptionController.class);

	@ExceptionHandler(EmailDuplicationExeption.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody String EmailDuplicationExeption() {
		String str = "이미 존재하는 이메일 입니다.";
		log.debug("{}", str);
		return StringToArray.convert(str);
	}
}
