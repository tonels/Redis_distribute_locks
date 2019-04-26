package as.exceptions;

/**
 * �Զ����쳣(CustomException)
 * @author liangshuai
 * @date 20190402
 */
public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public CustomException(String msg) {
		super(msg);
	}

	public CustomException() {
		super();
	}
}
