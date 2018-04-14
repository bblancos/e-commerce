package net.blanc.e_commerce.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.blanc.back_e_commerce.dao.CategoryDAO;
import net.blanc.back_e_commerce.dao.ProductDAO;
import net.blanc.back_e_commerce.dto.Category;
import net.blanc.back_e_commerce.dto.Product;
import net.blanc.e_commerce.util.FileUploadUtility;
import net.blanc.e_commerce.validator.ProductValidator;

@Controller
@RequestMapping("/manage")
public class ManagementController {
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);
	
	@RequestMapping(value="/products",method=RequestMethod.GET)
	public ModelAndView showManageProducts(@RequestParam(name="operation",required=false) String operation){
		
		ModelAndView mv= new ModelAndView("page");
		
		mv.addObject("userClickManageProduct",true);
		mv.addObject("title","Manage Products");
		Product nProduct= new Product();
		
		//set fields
		
		nProduct.setSupplierId(1);
		nProduct.setActive(true);
		mv.addObject("product",nProduct);
		
		if(operation!=null){
			if(operation.equals("product"))
			{
				mv.addObject("message","Product Submitted Succesfully");
			}
			else if(operation.equals("category")){
				mv.addObject("message","Category Submitted Succesfully");
			}
		}
		
		return mv;
		
	}
	
	//handling  product submission
	@RequestMapping(value="/products",method=RequestMethod.POST)
	public String handleProductSubmission(@Valid@ModelAttribute("product") Product mproduct,BindingResult results,Model model
			,HttpServletRequest request ){
		
		if(mproduct.getId() == 0){
		new ProductValidator().validate(mproduct,results);
		}
		
		else{
			if(!mproduct.getFile().getOriginalFilename().equals("")){
				new ProductValidator().validate(mproduct,results);
			}
		}
		//check if there any error
		if(results.hasErrors()){
			model.addAttribute("userClickManageProduct",true);
			model.addAttribute("title","Manage Products");
			model.addAttribute("message","Validation Failed for Product Submission");
			
			return  "page";
		}
		
		logger.info(mproduct.toString());
		
		if(mproduct.getId() == 0){
			productDAO.add(mproduct);
		}
		else{
			// update the product whie editing the product 
			
			productDAO.update(mproduct);
		}	
		
		
		if(!mproduct.getFile().getOriginalFilename().equals("")){
			FileUploadUtility.uploadFile(request,mproduct.getFile(),mproduct.getCode());
		}
		return "redirect:/manage/products?operation=product";
	}
	
	@RequestMapping(value="/{id}/product",method=RequestMethod.GET)
	public ModelAndView showEditProducts(@PathVariable int id ){
		
		ModelAndView mv= new ModelAndView("page");
		
		mv.addObject("userClickManageProduct",true);
		mv.addObject("title","Manage Products");		
		//fetch the pridict fro databse
		
		Product nProduct= productDAO.get(id);		
		//set fields
	
		mv.addObject("product",nProduct);		
		return mv;
		
	}
	
	
	
	
	@RequestMapping(value = "/product/{id}/activation", method=RequestMethod.POST)
	@ResponseBody
	public String managePostProductActivation(@PathVariable int id) {		
		Product product = productDAO.get(id);
		boolean isActive = product.isActive();
		product.setActive(!isActive);
		productDAO.update(product);		
		return (isActive)? "Product Dectivated Successfully!": "Product Activated Successfully";
	}
	
	//to handle categiory submission
	
	@RequestMapping(value="/category",method=RequestMethod.POST)
	public String handleCategorySubmission(@ModelAttribute Category category){
		//add new category
		categoryDAO.add(category);
		return "redirect:/manage/products?operation=category";
	}
	
	//returning cateories for all the request controller
	@ModelAttribute("categories")
	public List<Category> getCategories(){
		
		return categoryDAO.list();
		
	}
	
	@ModelAttribute("category")
	public Category getCategory(){
		return new Category();
	}

}
