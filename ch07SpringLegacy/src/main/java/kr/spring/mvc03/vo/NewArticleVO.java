package kr.spring.mvc03.vo;

public class NewArticleVO {
	private String parentId;
	private String title;
	private String content;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "NewArticleVO [parentId=" + parentId + ", title=" + title + ", content=" + content + "]";
	}
	
	
}
