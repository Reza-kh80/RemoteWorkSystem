
public class Report {

	private String title;
	private String body;

	public Report(String title, String body) {
		this.title = title;
		this.body = body;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "--------------------------------\n" + this.getTitle() + "\n" + this.getBody()
				+ "\n--------------------------------\n";
	}
}