package net.blanc.e_commerce.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import net.blanc.back_e_commerce.dto.Product;

public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		Product product=(Product) target;
		
		//wheather file has been selected or not
		
		if(product.getFile() == null || product.getFile().getOriginalFilename()
				.equals("")){
			errors.rejectValue("file",null,"Please Select an Image File to upload");
			return ;
		}
		
		if(! (	product.getFile().getContentType().equals("image/jpeg") || 
				product.getFile().getContentType().equals("image/png") ||
				product.getFile().getContentType().equals("image/gif")	
				))
		{
			errors.rejectValue("file",null ,"Please use only image for upload");
			return;
		}
	}

}
