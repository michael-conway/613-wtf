package parser;

import java.util.ArrayList;
import java.util.List;

public class ReutersInfo {

	private List<String> topics = new ArrayList<String>();
	private List<String> places = new ArrayList<String>();
	private List<String> people = new ArrayList<String>();
	private String docName;
	private String title;
	private String body;
	public List<String> getTopics() {
		return topics;
	}
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}
	public List<String> getPlaces() {
		return places;
	}
	public void setPlaces(List<String> places) {
		this.places = places;
	}
	public List<String> getPeople() {
		return people;
	}
	public void setPeople(List<String> people) {
		this.people = people;
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
		StringBuilder builder = new StringBuilder();
		builder.append("ReutersInfo [");
		if (topics != null)
			builder.append("topics=").append(topics).append(", ");
		if (places != null)
			builder.append("places=").append(places).append(", ");
		if (people != null)
			builder.append("people=").append(people).append(", ");
		if (title != null)
			builder.append("title=").append(title).append(", ");
		if (body != null)
			builder.append("body=").append(body);
		builder.append("]");
		return builder.toString();
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	
	
}
