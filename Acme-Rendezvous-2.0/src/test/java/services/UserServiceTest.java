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
import domain.Citizen;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/junit.xml"})
@Transactional
public class UserServiceTest extends AbstractTest{
	
	@Autowired
	private UserService userService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//Test Login---------------------------------------
	//5.1 Acme-Rendezvous Do the same as an actor who is not authenticated, but register to the system.
	@Test
	public void loginDriver(){
		final Object testingData[][] = {
			{
				//creando user correctamente
				"user1",null
			}
			,{
				//creando user sin name
				"DonManuee", IllegalArgumentException.class
			}
		};
		
		for (int i = 0; i<testingData.length; i++){
			this.templateLoginUser((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}
	
	private void templateLoginUser(final String user, final Class<?> expected){
		Class<?> caught;
		caught = null;
		
		try{
			super.authenticate(user);
			this.unauthenticate();
			this.userService.flush();
			
		}catch (final Throwable oops){
			caught = oops.getClass();
			this.entityManager.clear();
		}
		
		this.checkExceptions(expected, caught);
	}
	
	
	//Test Registro---------------------------------------
	// 4.1 Acme-Rendezvous  Register to the system as a user.
	@Test
	public void registrationUserDriver(){
		final Object testingData[][] = {
			{
				//creando user correctamente
				"user1", "surname1", "email1@gmail.com", "612345678", "Address1", "18/10/1993", "userName1", "password", null
			}
			,{
				//creando user sin name
				"", "surname1", "email1@gmail.com", "612345678", "Address1", "18/10/1993", "userName1", "password", DataIntegrityViolationException.class
			}
			,{
				//creando user sin surname
				"user1", "", "email1@gmail.com", "612345678", "Address1", "18/10/1993", "userName1", "password", DataIntegrityViolationException.class
			}
			
			,{
				//creando user sin email
				"user1", "surname1", "", "612345678", "Address1", "18/10/1993", "userName1", "password", DataIntegrityViolationException.class
			}
			
			,{
				//creando user sin teléfono
				"user1", "surname1", "email1@gmail.com", "", "Address1", "18/10/1993", "userName1", "password", DataIntegrityViolationException.class
			}
			
			,{
				//creando user sin dirección
				"user1", "surname1", "email1@gmail.com", "612345678", "", "18/10/1993", "userName1", "password", DataIntegrityViolationException.class
			}
			,{
				//creando user sin fecha de nacimiento
				"user1", "surname1", "email1@gmail.com", "612345678", "Address1", "", "userName1", "password", DataIntegrityViolationException.class
			}
			,{
				//creando user sin username
				"user1", "surname1", "email1@gmail.com", "612345678", "Address1", "18/10/1993", "", "password", javax.validation.ConstraintViolationException.class
			}
			,{
				//creando user sin contraseña
				"user1", "surname1", "email1@gmail.com", "612345678", "Address1", "18/10/1993", "userName1", "", DataIntegrityViolationException.class
			}
		};
		
		for (int i = 0; i<testingData.length; i++){
			this.templateCreateUser((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6], (String) testingData[i][7], (Class<?>) testingData[i][8]);
		}
	}
	
	
	private void templateCreateUser(final String name, final String surname, final String email, final String phone, final String address, final String birth, final String username, final String password, final Class<?> expected){
		Class<?> caught;
		Citizen user;
		caught = null;
		Date fecha = new Date();
		
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		try{
			fecha = pattern.parse(birth);
		}catch(ParseException e){
			e.getClass();
		}
		
		try{
			user = this.userService.create();
			
			user.setName(name);
			user.setSurname(surname);
			user.setEmail(email);
			user.setPhone(phone);
			user.setAddress(address);
			user.setBirth(fecha);
			user.getUserAccount().setUsername(username);
			user.getUserAccount().setPassword(password);
			
			user = this.userService.save(user);
			
			this.userService.flush();
		}catch (final Throwable oops){
			caught = oops.getClass();
			this.entityManager.clear();
		}
		
		this.checkExceptions(expected, caught);
	}
	
	// Test Edit USer
	@Test
	public void editUserDriver(){
		final Object testingData[][] = {
			{
				//creando user correctamente
				"user1", "surname1", "email1@gmail.com", "612345678", "Address1", "18/10/1993",  null
			}
			,{
				//creando user sin name
				"", "surname1", "email1@gmail.com", "612345678", "Address1", "18/10/1993",  javax.validation.ConstraintViolationException.class
			}
		};
		
		for (int i = 0; i<testingData.length; i++){
			this.templateEditUser((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
		}
	}
	
	
	private void templateEditUser(final String name, final String surname, final String email, final String phone, final String address, final String birth,  final Class<?> expected){
		Class<?> caught;
		Citizen user;
		caught = null;
		Date fecha = new Date();
		
		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
		try{
			fecha = pattern.parse(birth);
		}catch(ParseException e){
			e.getClass();
		}
		
		try{
			user = this.userService.create();
			
			user.setName(name);
			user.setSurname(surname);
			user.setEmail(email);
			user.setPhone(phone);
			user.setAddress(address);
			user.setBirth(fecha);
			
			user = this.userService.save(user);
			
			this.userService.flush();
		}catch (final Throwable oops){
			caught = oops.getClass();
			this.entityManager.clear();
		}
		
		this.checkExceptions(expected, caught);
	}
	
	
}
