package controllers.user;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.RequestService;
import services.ServiceService;
import services.UserService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.AbstractController;
import domain.Request;
import domain.Service;
import forms.RequestForm;

@Controller
@RequestMapping("/request/user")
public class RequestUserController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private RequestService requestService;
	
	@Autowired
	private ServiceService serviceService;

	@Autowired
	private UserService userService;

	// Constructors --------------------------------------------------

	public RequestUserController() {
		super();
	}

	// Creation ------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int rendezvousId, @CookieValue(value = "CREDITCARD", defaultValue = "") String creditCardCookie) throws JsonParseException, JsonMappingException, IOException{
		
		Assert.isTrue(userService.checkAuthority());
		
		Request request = requestService.create(rendezvousId);
		RequestForm requestForm = requestService.construct(request);
		
		if(!creditCardCookie.equals("")){
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {};
			Map<String, String> map = mapper.readValue(creditCardCookie, typeRef);
			
			requestForm.setHolder(map.get("holder"));
			requestForm.setBrand(map.get("brand"));
			requestForm.setNumber(map.get("number"));
			requestForm.setExpirationMonth(Integer.valueOf(map.get("expirationMonth")));
			requestForm.setExpirationYear(Integer.valueOf(map.get("expirationYear")));
			requestForm.setCvv(Integer.valueOf(map.get("cvv")));
		}
		
		ModelAndView result = createEditModelAndView(requestForm);
		
		return result;
	}
	
	// Edition -------------------------------------------------------
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RequestForm requestForm, BindingResult binding, HttpServletResponse response) throws JsonProcessingException{
		
		Assert.isTrue(userService.checkAuthority());
		
		ModelAndView result;
		Request request;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(requestForm);
		}else{
			try{
				request = requestService.reconstruct(requestForm, binding);
				requestService.save(request);
				result = new ModelAndView("redirect:/service/list.do?rendezvousId="+requestForm.getRendezvousId());
				
				Map<String, String> map = new HashMap<>();
				map.put("holder", request.getCreditCard().getHolder());
				map.put("brand", request.getCreditCard().getBrand());
				map.put("number", request.getCreditCard().getNumber());
				map.put("expirationMonth", String.valueOf(request.getCreditCard().getExpirationMonth()));
				map.put("expirationYear", String.valueOf(request.getCreditCard().getExpirationYear()));
				map.put("cvv", String.valueOf(request.getCreditCard().getCvv()));
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonCreditCard= mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
				
				Cookie creditCardCookie = new Cookie("CREDITCARD", jsonCreditCard);
				creditCardCookie.setMaxAge(1000000);
				creditCardCookie.setPath("/");
				response.addCookie(creditCardCookie);
			}catch(Throwable oops){
				result = createEditModelAndView(requestForm, "request.commit.error");
			}
		}
		
		
		
		return result;
	
	}
	
	// Ancillary methods ---------------------------------------------
	
	protected ModelAndView createEditModelAndView(RequestForm requestForm){
		
		ModelAndView result = createEditModelAndView(requestForm, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(RequestForm requestForm, String message){
		
		Assert.isTrue(userService.checkAuthority());
		
		Collection<Service> services = serviceService.findAvalibleServicesByRendezvousId(requestForm.getRendezvousId());
		
		ModelAndView result = new ModelAndView("request/create");
		result.addObject("requestForm", requestForm);
		result.addObject("services", services);
		result.addObject("message", message);
		
		return result;
	}

}
