package com.nerdcoder.blog.payload;

import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
		description = "PostDto Model Information"
)
public class PostDtoV2 {
	
	private long id;
	
	@Schema(
			description = "Blog Post Title"
	)
	// title should not be empty of null
	// title should have atleast 2 characters
	@NotEmpty
	@Size(min = 2, message = "Post title should have atleast 2 characters")
	private String title;
	
	@Schema(
			description = "Blog Post Description"
	)
	// post description should be not null or empty
	// post description should have at least 10 characters
	@NotEmpty
	@Size(min = 10, message = "Post Description should have atleast 10 characters")
	private String description;
	
	@Schema(
			description = "Blog Post Content"
	)
	// post content should not be null or empty
	@NotEmpty
	private String content;
	private Set<CommentDto> comments;
	
	@Schema(
			description = "Blog Post Category"
	)
	private Long categoryId;
	
	private List<String> tags;

}
