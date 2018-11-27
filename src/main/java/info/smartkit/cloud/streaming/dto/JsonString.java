package info.smartkit.cloud.streaming.dto;

public class JsonString {
	public JsonString(String body) {
		this.body = body;
	}

	// Setter,getters
	private String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "JsonString{" +
				"body='" + body + '\'' +
				'}';
	}
}
