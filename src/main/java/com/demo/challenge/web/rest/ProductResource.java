package com.demo.challenge.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.demo.challenge.web.rest.vm.ProductVM;

/**
 * REST controller for Products
 * 
 * @author Prashant
 *
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	/**
	 * POST /products : Create product.
	 *
	 * @param productVM
	 *            the product View Model
	 * @return the ResponseEntity with Product object with status 200 (Success)
	 *         if the user is logged in or 400 (Bad Request) if the token
	 *         generation fails
	 */
	@PostMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<ProductVM> createProduct(
			@Valid @RequestBody ProductVM productVM,
			@Valid @RequestHeader("Authorization") String authorization) {

		log.debug("REST request to create product resource : {}", productVM);

		productVM.setProductId(1234);
		return ResponseEntity.ok(productVM);
	}

	/**
	 * GET /products : get all the products.
	 *
	 * @return the ResponseEntity with status 200 (OK) and with product list, or
	 *         with status 404 (Not Found)
	 */
	@GetMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public List<ProductVM> getProducts(
			@Valid @RequestHeader("Authorization") String authorization) {
		log.debug("REST request to get all products");

		List<ProductVM> response = new ArrayList<ProductVM>();
		response.add(new ProductVM(1111, "Laptop", "Electronics"));
		response.add(new ProductVM(1112, "Origin", "Books"));
		response.add(new ProductVM(1113, "XYZ", "Fashion"));

		return response;
	}
}
