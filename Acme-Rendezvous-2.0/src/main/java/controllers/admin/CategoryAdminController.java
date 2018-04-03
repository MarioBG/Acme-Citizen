package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;
import forms.CategoryForm;

@Controller
@RequestMapping("/category/admin")
public class CategoryAdminController extends AbstractController {

	// Services ----------------------------
	@Autowired
	private CategoryService categoryService;

	
	// Constructors ------------------------
	public CategoryAdminController(){
		super();
	}
	
	// Edit -------------------------------
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId){
		final Category category = this.categoryService.findOne(categoryId);
		final CategoryForm categoryForm = this.categoryService.construct(category);
		
		final ModelAndView res = this.createEditModelAndView(categoryForm);
		return res;
	}
	
	
	// Save ------------------------------
	@RequestMapping(value="/edit",method=RequestMethod.POST, params="save")
	public ModelAndView save(CategoryForm categoryForm, final BindingResult binding){
		ModelAndView res;
		
		if(binding.hasErrors()){
			res = this.createEditModelAndView(categoryForm, "category.params.errors");
		}else{
			try{
				Category category = this.categoryService.reconstruct(categoryForm, binding);
				this.categoryService.save(category);
				res = new ModelAndView("redirect:/category/list.do");
				
			}catch (final Throwable oops) {
				res = this.createEditModelAndView(categoryForm,"category.commit.error");
			}
		}
		return res;
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST, params="save")
	public ModelAndView saveCreate(CategoryForm categoryForm, final BindingResult binding){
		ModelAndView res;
		
		if(binding.hasErrors()){
			res = this.createEditModelAndView(categoryForm, "category.params.errors");
		}else{
			try{
				Category category = this.categoryService.reconstruct(categoryForm, binding);
				this.categoryService.save(category);
				res = new ModelAndView("redirect:/category/list.do");
				
			}catch (final Throwable oops) {
				res = this.createEditModelAndView(categoryForm,"category.commit.error");
			}
		}
		return res;
	}
	
	// Create -----------------------------
		@RequestMapping(value="/create", method=RequestMethod.GET)
		public ModelAndView create(){
			ModelAndView res;
			Category category;
			CategoryForm categoryForm;
			
			category = this.categoryService.create();
			categoryForm = this.categoryService.construct(category);
			
			res = this.createEditModelAndView(categoryForm);
			return res;
		}


	// Delete -----------------------------
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int categoryId){
		Category category;
		
		category =this.categoryService.findOne(categoryId);
		this.categoryService.delete(category);
		
		return new ModelAndView("redirect:/category/list.do");
	}
	
	// Ancillary methods -----------------------------------
	
	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm) {
		ModelAndView res;
		res = this.createEditModelAndView(categoryForm,null);
		return res;
	}

	protected ModelAndView createEditModelAndView(final CategoryForm categoryForm,
			final String message) {
		ModelAndView res;
		Collection<Category> categories;
		categories = this.categoryService.findAll();
		
		res = new ModelAndView("category/edit");
		res.addObject("categoryForm",categoryForm);
		res.addObject("categories",categories);
		res.addObject("message",message);
//		res.addObject("requestURI","category/admin/edit.do");
		
		return res;
	}
}
