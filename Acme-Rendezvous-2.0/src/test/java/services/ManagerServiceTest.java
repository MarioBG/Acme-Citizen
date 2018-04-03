package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class ManagerServiceTest extends AbstractTest{
	
	@Autowired
	private ManagerService managerService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	//Test Login---------------------------------------
	
		@Test
		public void loginDriver(){
			final Object testingData[][] = {
				{
					//creando manager correctamente
					"manager1",null
				}
				,{
					//creando manager sin name
					"DonManuee", IllegalArgumentException.class
				}
			};
			
			for (int i = 0; i<testingData.length; i++){
				this.templateLoginManager((String) testingData[i][0], (Class<?>) testingData[i][1]);
			}
		}
		
		private void templateLoginManager(final String manager, final Class<?> expected){
			Class<?> caught;
			caught = null;
			
			try{
				super.authenticate(manager);
				this.unauthenticate();
				this.managerService.flush();
				
			}catch (final Throwable oops){
				caught = oops.getClass();
				this.entityManager.clear();
			}
			
			this.checkExceptions(expected, caught);
		}
		
	@Test
	public void registrationManagerDriver(){
		final Object testingData[][] = {
			{
				//creando manager correctamente
				"manager1", "surname1", "1A-2B" ,"email1@gmail.com", "612345678", "Address1", "18/10/1993", "managerName1", "password", null
			}
			,{
				//creando manager sin name
				"", "surname1","1A-2B" , "email1@gmail.com", "612345678", "Address1", "18/10/1993", "managerName1", "password", DataIntegrityViolationException.class
			}
			,{
				//creando manager sin surname
				"manager1", "","1A-2B" , "email1@gmail.com", "612345678", "Address1", "18/10/1993", "managerName1", "password", DataIntegrityViolationException.class
			}
			
			,{
				//creando manager sin email
				"manager1", "surname1","1A-2B" , "", "612345678", "Address1", "18/10/1993", "managerName1", "password", DataIntegrityViolationException.class
			}
			
			,{
				//creando manager sin teléfono
				"manager1", "surname1","1A-2B" , "email1@gmail.com", "", "Address1", "18/10/1993", "managerName1", "password", DataIntegrityViolationException.class
			}
			
			,{
				//creando manager sin dirección
				"manager1", "surname1","1A-2B" , "email1@gmail.com", "612345678", "", "18/10/1993", "managerName1", "password", DataIntegrityViolationException.class
			}
			,{
				//creando manager sin fecha de nacimiento
				"manager1", "surname1","1A-2B" , "email1@gmail.com", "612345678", "Address1", "", "managerName1", "password", DataIntegrityViolationException.class
			}
			,{
				//creando manager sin username
				"manager1", "surname1","1A-2B" , "email1@gmail.com", "612345678", "Address1", "18/10/1993", "", "password", javax.validation.ConstraintViolationException.class
			}
			,{
				//creando manager sin contraseña
				"manager1", "surname1","1A-2B" , "email1@gmail.com", "612345678", "Address1", "18/10/1993", "managerName1", "", DataIntegrityViolationException.class
			}
			,{
				//creando manager sin VAT
				"manager1", "surname1", "" ,"email1@gmail.com", "612345678", "Address1", "18/10/1993", "managerName1", "password", DataIntegrityViolationException.class
			}
		};
		
		for (int i = 0; i<testingData.length; i++){
			this.templateCreateManager((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (String) testingData[i][8], (Class<?>) testingData[i][9]);
		}
	}
	
	private void templateCreateManager(final String name, final String surname,final String vat, final String email, final String phone, final String address, final String birth, final String username, final String password, final Class<?> expected){
		Class<?> caught;
		Manager manager;
		caught = null;
		Date fecha = new Date();
		
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		try{
			fecha = pattern.parse(birth);
		}catch(ParseException e){
			e.getClass();
		}
		
		try{
			manager = this.managerService.create();
			
			manager.setName(name);
			manager.setSurname(surname);
			manager.setEmail(email);
			manager.setPhone(phone);
			manager.setAddress(address);
			manager.setBirth(fecha);
			manager.getUserAccount().setUsername(username);
			manager.getUserAccount().setPassword(password);
			manager.setVat(vat);
			
			
			manager = this.managerService.save(manager);
			
			this.managerService.flush();
		}catch (final Throwable oops){
			caught = oops.getClass();
			this.entityManager.clear();
		}
		
		this.checkExceptions(expected, caught);
	}

	@Test
	public void editManagerDriver(){
		final Object testingData[][] = {
			{
				//creando manager correctamente
				"manager1", "surname1", "1A-2B" ,"email1@gmail.com", "612345678", "Address1", "18/10/1993",  null
			}
			,{
				//creando manager sin name
				"", "surname1","1A-2B" , "email1@gmail.com", "612345678", "Address1", "18/10/1993",  javax.validation.ConstraintViolationException.class
			}

		};
		
		for (int i = 0; i<testingData.length; i++){
			this.templateEditManager((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],  (Class<?>) testingData[i][7]);
		}
	}
	
	private void templateEditManager(final String name, final String surname,final String vat, final String email, final String phone, final String address, final String birth,  final Class<?> expected){
		Class<?> caught;
		Manager manager;
		caught = null;
		Date fecha = new Date();
		
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		try{
			fecha = pattern.parse(birth);
		}catch(ParseException e){
			e.getClass();
		}
		
		try{
			manager = this.managerService.create();
			
			manager.setName(name);
			manager.setSurname(surname);
			manager.setEmail(email);
			manager.setPhone(phone);
			manager.setAddress(address);
			manager.setBirth(fecha);
			manager.setVat(vat);
			
			
			manager = this.managerService.save(manager);
			
			this.managerService.flush();
		}catch (final Throwable oops){
			caught = oops.getClass();
			this.entityManager.clear();
		}
		
		this.checkExceptions(expected, caught);
	}
	
}
