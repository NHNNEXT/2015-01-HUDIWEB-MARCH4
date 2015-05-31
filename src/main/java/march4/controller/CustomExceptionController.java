package march4.controller;

import java.util.ArrayList;
import java.util.List;

import march4.exception.EmailDuplicationExeption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionController {
	//TODO class 명을 바꾸면 왜 작동이 안될까?
	private static final Logger log = LoggerFactory.getLogger(CustomExceptionController.class);

	@ExceptionHandler(EmailDuplicationExeption.class)
	@RequestMapping(produces="text/plain;charset=UTF-8")
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody List<String> EmailDuplicationExeption() {
		
		List<String> tempList = new ArrayList<String>();
		String str = "이미 존재하는 이메일 입니다.";
		tempList.add(str);
		log.debug("{}", str);
		return tempList;
	}
}
