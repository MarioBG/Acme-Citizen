
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CategoryRepository;
import domain.Category;
import domain.Service;
import forms.CategoryForm;

@org.springframework.stereotype.Service
@Transactional
public class CategoryService {

	// Managed repository

	@Autowired
	private CategoryRepository	categoryRepository;

	//Other services
	
	@Autowired
	private AdminService		adminService;
	
	@Autowired
	private ManagerService		managerService;
	
	@Autowired
	private Validator validator;


	// Constructors

	public CategoryService() {
		super();
	}

	// Simple CRUD methods

	public Category create() {

		Assert.isTrue(adminService.checkAuthority());
		
		final Category result = new Category();
		result.setCategories(new ArrayList<Category>());
		result.setServices(new ArrayList<Service>());

		return result;
	}

	public Collection<Category> findAll() {

		final Collection<Category> result = this.categoryRepository.findAll();
		return result;
	}

	public Category findOne(final int categoryId) {

		Assert.isTrue(categoryId != 0);

		final Category result = this.categoryRepository.findOne(categoryId);
		return result;
	}
	
	public Category findOneToEdit(final int categoryId) {

		Assert.isTrue(adminService.checkAuthority());

		final Category result = this.categoryRepository.findOne(categoryId);
		return result;
	}

	public Category save(final Category category) {
		
		Assert.isTrue(adminService.checkAuthority());
		Assert.notNull(category);

		Category result = this.categoryRepository.save(category);

		if (result.getCategoryParent() != null && !category.getCategoryParent().getCategories().contains(result)) {
			result.getCategoryParent().getCategories().add(result);
		}else if (result.getCategoryParent() == null){
			for(Category c : findAll()){
				if(category.getCategories().contains(result)){
					c.getCategories().remove(result);
				}
			}
		}

		return result;
	}

	public void delete(final Category category) {
		
		Assert.isTrue(adminService.checkAuthority());
		Assert.isTrue(category.getServices().isEmpty());

		if(category.getCategoryParent() != null){
			category.getCategoryParent().getCategories().remove(category);
		}
		
		if(category.getCategories().isEmpty() == false){
			for (final Category c : category.getCategories()) {
				c.setCategoryParent(category.getCategoryParent());
				if(category.getCategoryParent() != null){
					category.getCategoryParent().getCategories().add(c);
				}
			}
		}
		
		this.categoryRepository.delete(category);
	}

	
	// Other business methods

	public CategoryForm construct(Category category) {
		
		CategoryForm res = new CategoryForm();
		
		res.setId(category.getId());
		res.setName(category.getName());
		res.setDescription(category.getDescription());
		if(category.getCategoryParent() != null){
			res.setCategoryParentId(category.getCategoryParent().getId());
		}
		
		return res;
	}
	
	public Category reconstruct(CategoryForm categoryForm, BindingResult binding){

		Assert.notNull(categoryForm);
		
		Category res;

		if(categoryForm.getId() == 0){
			res = create();
		}else{
			res = findOne(categoryForm.getId());
		}

		res.setName(categoryForm.getName());
		res.setDescription(categoryForm.getDescription());
		if(categoryForm.getCategoryParentId() != 0){
			res.setCategoryParent(findOne(categoryForm.getCategoryParentId()));
		}else{
			res.setCategoryParent(null);
		}
		
		if(binding!=null)
			validator.validate(res, binding);
		
		return res;
	}

	public Collection<Category> findCategories() {
		Collection<Category> res = new ArrayList<Category>();
		res = this.categoryRepository.findCategories();
		return res;
	}

	public void flush() {
		this.categoryRepository.flush();
	}
}
