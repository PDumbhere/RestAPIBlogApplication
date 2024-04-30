package com.nerdcoder.blog.payload;

import java.util.List;

import com.nerdcoder.blog.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	private Long id;
	private String name;
	private String description;

}
