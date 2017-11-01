package com.gooalgene.mrna.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;
import java.util.Map;


@Document(collection = "classifys")
public class Classifys {
	
	@Id
	private String id;
	
	private String name;
	
	private String chinese;
	
	private List<Map<String,Object>> children;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Map<String, Object>> getChildren() {
		return children;
	}

	public void setChildren(List<Map<String, Object>> children) {
		this.children = children;
	}

}
