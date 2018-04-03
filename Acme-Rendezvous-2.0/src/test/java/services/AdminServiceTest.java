package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class AdminServiceTest extends AbstractTest{
	
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CommentService commentService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	//Test Login---------------------------------------
	
		@Test
		public void loginDriver(){
			final Object testingData[][] = {
				{
					//creando user correctamente
					"admin",null
				}
				,{
					//creando user sin name
					"DonManuee", IllegalArgumentException.class
				}
			};
			
			for (int i = 0; i<testingData.length; i++){
				this.templateLoginAdmin((String) testingData[i][0], (Class<?>) testingData[i][1]);
			}
		}
		
		private void templateLoginAdmin(final String admin, final Class<?> expected){
			Class<?> caught;
			caught = null;
			
			try{
				super.authenticate(admin);
				this.unauthenticate();
				this.adminService.flush();
				
			}catch (final Throwable oops){
				caught = oops.getClass();
				this.entityManager.clear();
			}
			
			this.checkExceptions(expected, caught);
		}
		

}
