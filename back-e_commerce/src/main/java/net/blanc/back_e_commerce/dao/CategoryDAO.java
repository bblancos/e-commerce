package net.blanc.back_e_commerce.dao;

import java.util.List;

import net.blanc.back_e_commerce.dto.Category;


public interface CategoryDAO {
		
	Category get(int id);
	List<Category> list();
	boolean add(Category category);	
	boolean update(Category category);
	boolean delete(Category category);

}
