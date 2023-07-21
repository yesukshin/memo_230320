package com.memo.post.domain;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.ToString;

@ToString
@Data //lombok에서 getter, setter를 만들어 준다
public class Post {
	
	private int id;
	private int userId;
	private String subject;
	private String content;
	private String imagePath;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
}
