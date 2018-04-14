package net.blanc.back_e_commerce.dao;

import java.util.List;

import net.blanc.back_e_commerce.dto.Product;

public interface ProductDAO {
	
	Product get(int productId);
	List<Product> list();	
	boolean add(Product product);
	boolean update(Product product);
	boolean delete(Product product);
	
	//Businees Methods for products
	
	List<Product> listActiveProducts();	
	List<Product> listActiveProductsByCategory(int categoryId);
	List<Product> getLatestActiveProducts(int count);

}
