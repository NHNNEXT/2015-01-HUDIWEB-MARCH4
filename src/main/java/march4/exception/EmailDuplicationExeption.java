package march4.exception;

import org.springframework.dao.DuplicateKeyException;

public class EmailDuplicationExeption extends DuplicateKeyException {

	private static final long serialVersionUID = 1L;

	public EmailDuplicationExeption(String msg, Throwable cause) {
		super(msg, cause);
	}
}
