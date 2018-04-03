package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Category;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest{
	
	// The SUT -------------------------------------
	
	@Autowired
	private CategoryService categoryService;
	
	
	// Create and Save Test -------------------------
	
	@Test
	public void driverCreateAndSave(){
		Object testingData[][]={
			// positive test
			{
				"admin", "name Category", "description Category", "category1", null
			},
			// negative test: name blank
			{
				"admin", " ", "description Category", "category1", javax.validation.ConstraintViolationException.class
			},
			// negative test: user1
			{
				"user1", "name Category", "description Category", "category1", IllegalArgumentException.class
			},
		};
		
		for (int i=0; i<testingData.length; i++){
			templateCreateAndSave((String)testingData[i][0],
					(String)testingData[i][1],
					(String)testingData[i][2],
					super.getEntityId((String)testingData[i][3]),
					(Class<?>) testingData[i][4]);
		}
	}

	protected void templateCreateAndSave(String username, String name,
			String description, int parentId, Class<?> expected) {
		
		Category category;
		Category parent;
		Class<?> caught;
		
		caught = null;
		
		try{
			authenticate(username);
			category = this.categoryService.create();
			category.setName(name);
			category.setDescription(description);
			parent = this.categoryService.findOne(parentId);
			category.setCategoryParent(parent);
			category = this.categoryService.save(category);
			this.categoryService.flush();
			unauthenticate();
		}catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
	}
	
	
	
	// Edit and Save Test -------------------------
	
	@Test
	public void driverEditTest(){
		Object testingData[][] = {
				// positive test
				{
					"admin", "category2", "new name", "new description", "category1", null
				},
				// negative test: description blank
				{
					"admin", "category2", "new name", " ", "category1", javax.validation.ConstraintViolationException.class
				},
				// negative test: user1
				{
					"user1", "category2", "new name", "new description", "category1", IllegalArgumentException.class
				},
		};
		for (int i=0; i<testingData.length; i++){
			templateEdit((String)testingData[i][0],
					super.getEntityId((String)testingData[i][1]),
					(String)testingData[i][2],
					(String)testingData[i][3],
					super.getEntityId((String)testingData[i][4]),
					(Class<?>) testingData[i][5]);
		}
	}
	
	protected void templateEdit(String username, int categoryId, String name,
			String description, int parentId, Class<?> expected) {
		
		Category category;
		Category parent;
		Class<?> caught;
		
		caught = null;
		
		try{
			authenticate(username);
			category = this.categoryService.findOne(categoryId);
			category.setName(name);
			category.setDescription(description);
			parent = this.categoryService.findOne(parentId);
			category.setCategoryParent(parent);
			category = this.categoryService.save(category);
			this.categoryService.flush();
			unauthenticate();
		}catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
		
	}
	
	
	// Delete Test -------------------------
	
		@Test
		public void driverDeleteTest(){
			Object testingData[][] = {
					// positive test
					{
						"admin", "category5", null
					},
					// negative test: category.getServices not empty
					{
						"admin", "category2", IllegalArgumentException.class
					},
					// negative test: user1
					{
						"user1", "category5", IllegalArgumentException.class
					},
					
			};
			for (int i=0; i<testingData.length; i++){
				templateDelete((String)testingData[i][0],
						super.getEntityId((String)testingData[i][1]),
						(Class<?>) testingData[i][2]);
			}
		}
		
		protected void templateDelete(String username, int categoryId, Class<?> expected) {
			
			Category category;
			Class<?> caught;
			
			caught = null;
			
			try{
				authenticate(username);
				category = this.categoryService.findOne(categoryId);
				this.categoryService.delete(category);
				this.categoryService.flush();
				unauthenticate();
			}catch (Throwable oops) {
				caught = oops.getClass();
			}
			checkExceptions(expected, caught);
			
		}
	
}
