package com.nerdcoder.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nerdcoder.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
