package com.demo.challenge.web.rest.vm;

import javax.validation.constraints.NotNull;


/**
 * View Model object for Products.
 * @author Prashant
 */
public class ProductVM {

	private long productId;
	
    @NotNull
    private String name;
    
    @NotNull
    private String category;

    public ProductVM() {
		// TODO Auto-generated constructor stub
	}
    
    public ProductVM(long productId, String name, String category) {
		this.productId = productId;
		this.name = name;
		this.category = category;
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
    
	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	@Override
    public String toString() {
        return "ProductVM{" +
            "name='" + name + '\'' +
            ", category=" + category +
            '}';
    }
}
