package fluxandmonoplayground;

public class CustomException extends Throwable {

	private String messages;

	public String getMessages() {
		return messages;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public CustomException(Throwable e) {
		this.messages = e.getMessage();
	}

}
