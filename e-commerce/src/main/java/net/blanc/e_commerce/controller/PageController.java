package net.blanc.e_commerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.blanc.back_e_commerce.dao.CategoryDAO;
import net.blanc.back_e_commerce.dao.ProductDAO;
import net.blanc.back_e_commerce.dto.Category;
import net.blanc.back_e_commerce.dto.Product;

@Controller

public class PageController {
	
	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value={"/home","/index"})
	public ModelAndView index(){
		ModelAndView mv=new ModelAndView("page");
		mv.addObject("title","Home");		
		
		logger.info("Inside PageController index Method - INFO");
		logger.debug("Inside PageController index Method - DEBUG");
		// passing list of categories		
		mv.addObject("categories",categoryDAO.list());
		mv.addObject("userClickHome",true);

		return mv; 
	}
	
	@RequestMapping(value={"/about"})
	public ModelAndView about(){
		ModelAndView mv=new ModelAndView("page");
		mv.addObject("title","About Us");
		mv.addObject("userClickAbout",true);

		return mv;
	}
	
	@RequestMapping(value={"/contact"})
	public ModelAndView contact(){
		ModelAndView mv=new ModelAndView("page");
		mv.addObject("title","Contact Us");
		mv.addObject("userClickContact",true);
		return mv;
	}
	
	/*
	 * Methods to load all the procusts based on vategory
	 */
	
	@RequestMapping(value="/show/all/products")
	public ModelAndView showAllProducts(){
		ModelAndView mv=new ModelAndView("page");
		mv.addObject("title","All Products");		
		// passing list of categories		
		mv.addObject("categories",categoryDAO.list());
		mv.addObject("userClickAllProducts",true);
		return mv; 
	} 
	
	@RequestMapping(value="/show/category/{id}/products")
	public ModelAndView showCategoryProducts(@PathVariable("id") int id){
		ModelAndView mv=new ModelAndView("page");		
		//categoryDao to fetch a single category
		Category category =  null;
		category= categoryDAO.get(id);
		mv.addObject("title",category.getName());		
		// passing list of categories		
		mv.addObject("categories",categoryDAO.list());
		//passing single category object
		mv.addObject("category",category);
		mv.addObject("userClickCategoryProducts",true);

		return mv; 
	}
	
	/**
	 * Viewwing Single Product 
	 */
	
	@RequestMapping(value="/show/{id}/product")
	public ModelAndView showSingleProduct(@PathVariable int id) {
		ModelAndView mv=new ModelAndView("page");
		Product product = productDAO.get(id);
		
		//update the view count
		product.setViews(product.getViews());
		productDAO.update(product);
		
		
		mv.addObject("title",product.getName());
		mv.addObject("product",product);
		
		mv.addObject("userClickShowProduct",true);
		
		
		
		return mv;
		
	}

	
	
}
