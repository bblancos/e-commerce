package net.blanc.back_e_commerce.test;


import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.blanc.back_e_commerce.dao.CategoryDAO;
import net.blanc.back_e_commerce.dto.Category;

public class CategoryTestCase {
	
	private static AnnotationConfigApplicationContext context;
	
	
	private static CategoryDAO categoryDAO;
	
	
	private Category category;
	
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("net.blanc.back_e_commerce");
		context.refresh();	
		categoryDAO= (CategoryDAO)context.getBean("categoryDAO");
		
	}
	
//	@Test
//	public void testAddCategory() {		
//		category = new Category();		
//		category.setName("Television");
//		category.setDescription("This is some description for Television!");
//		category.setImageURL("CAT_115.png");		
//		assertEquals("Successfully added a category inside the table!",true,categoryDAO.add(category));
//	}
	
//	@Test
//	public void testGetCategory() {		
//		category=categoryDAO.get(3);
//		assertEquals("Successfully fetched single category from the table","Television",category.getName());
//
//	}
	
//	
//	@Test
//	public void testUpdateCategory() {	
//		
//		category=categoryDAO.get(3);
//		category.setName("TV");
//		assertEquals("Successfully update single category in the table",true,categoryDAO.update(category));
//
//	}
//	
//	@Test
//	public void testDeletedCategory() {	
//		
//		category=categoryDAO.get(3);
//		category.setName("TV");
//		assertEquals("Successfully Deleted single category in the table",true,categoryDAO.delete(category));
//
//	}
//	
//	@Test
//	public void testListCategory() {	
//		
//		 
//		assertEquals("Successfully Fetched the List category in the table",2,categoryDAO.list().size());
//
//	}
	
	@Test
	public void testCRUDCategory(){
		//add operation
		category = new Category();		
		category.setName("Laptop");
		category.setDescription("This is some description for Television!");
		category.setImageURL("CAT_1.png");		
		assertEquals("Successfully added a category inside the table!",true,categoryDAO.add(category));
	
		category = new Category();		
		category.setName("Television");
		category.setDescription("This is some description for Television!");
		category.setImageURL("CAT_2.png");		
		assertEquals("Successfully added a category inside the table!",true,categoryDAO.add(category));
	
		//Updating category
		
		category=categoryDAO.get(2);
		category.setName("TV");
		assertEquals("Successfully update single category in the table",true,categoryDAO.update(category));

		//Deleting category
		
		assertEquals("Successfully Deleted single category in the table",true,categoryDAO.delete(category));

		//Getting the list of all
		
		assertEquals("Successfully Fetched the List category in the table",1,categoryDAO.list().size());

		
	}
	
	
 }
