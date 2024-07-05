package com.codingdojo.milagros.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.codingdojo.milagros.models.Event;
import com.codingdojo.milagros.models.Message;
import com.codingdojo.milagros.models.Province;
import com.codingdojo.milagros.models.User;
import com.codingdojo.milagros.services.AppServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class EventsControllers {
	 
	@Autowired
	private AppServices serv;
	
	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, @ModelAttribute ("newEvent") Event newEvent, Model model) {
		//Validación de que el usuario inició sesión
		User userTemp = (User) session.getAttribute("userInSession"); //Obj User o null
		
		if(userTemp == null) {
			return "redirect:/";
		}
		
		//envio mi usuario
		 User myUser= serv.getUser(userTemp.getId());
		 model.addAttribute("user",myUser);
		
		 
		 List<Event> eventsNear= serv.getEventsNear(myUser.getProvince());
		 model.addAttribute("eventNear", eventsNear);
		 
		 List<Event> eventsNotNear= serv.getEventsNotNear(myUser.getProvince());
		 model.addAttribute("eventNotNear", eventsNotNear);
		 
		 model.addAttribute("provinces",Province.Provinces);
		 
		 
		 
		return "dashboard.jsp";
	}
	
	@PostMapping("/createEvent")
	public String createEvent(HttpSession session, /*persistencia de datos*/
			@Valid @ModelAttribute("newEvent") Event event, BindingResult result,Model model) {
	
				User userTemp = (User) session.getAttribute("userInSession"); //Obj User o null
						
						if(userTemp == null) {
							return "redirect:/";
						}
		if(result.hasErrors()) {
			 User myUser= serv.getUser(userTemp.getId());
			 model.addAttribute("user",myUser);
			
			 List<Event> eventsNear= serv.getEventsNear(myUser.getProvince());
			 model.addAttribute("eventNear", eventsNear);
			 
			 List<Event> eventsNotNear= serv.getEventsNotNear(myUser.getProvince());
			 model.addAttribute("eventNotNear", eventsNotNear);
			 
			 model.addAttribute("provinces",Province.Provinces);
			 
			
			return "dashboard.jsp";
			
		}
		else {
			
			serv.saveEvent(event);
			return "redirect:/dashboard";
		}
		
		
	}
	
	
	@GetMapping("/join/{eventId}")
	public String join (@PathVariable("eventId") Long eventId,HttpSession session) {
		
		User userTemp = (User) session.getAttribute("userInSession"); //Obj User o null
		
		if(userTemp == null) {
			return "redirect:/";
		}
		
		
		serv.joinEvent(userTemp.getId(), eventId);
		
		return "redirect:/dashboard";
		
	}
	
	
	@GetMapping("/cancel/{eventId}")
	public String cancel(@PathVariable("eventId") Long eventId,HttpSession session) {
		
		User userTemp = (User) session.getAttribute("userInSession"); //Obj User o null
		
		if(userTemp == null) {
			return "redirect:/";
		}
		
		
		serv.cancelEvent(userTemp.getId(), eventId);
		return "redirect:/dashboard";
		
	
	}
	
	
	@GetMapping("/event/{id}")
	public String event(@PathVariable("id") Long id,HttpSession session, Model model,@ModelAttribute ("message") Message message) {
		
		User userTemp = (User) session.getAttribute("userInSession"); //Obj User o null
				
				if(userTemp == null) {
					return "redirect:/";
				}
				
		Event event = serv.getEvent(id);
		model.addAttribute("event",event);
		return "event.jsp";
	}
	
	//--------EDITAR-------------------------------------------
	@GetMapping("/event/edit/{eventid}")
	public String editarEvento(@PathVariable("eventid") Long id,HttpSession session,
			@ModelAttribute("event") Event event, Model model) {
		
		User userTemp = (User) session.getAttribute("userInSession"); //Obj User o null
		if(userTemp == null) {
		return "redirect:/";
		}
		
		
		Event eventTraido = serv.findEvent(id);
		model.addAttribute("event", eventTraido);
		model.addAttribute("provinces",Province.Provinces);
		return "editEvent.jsp";
		
	}
	
	@PutMapping("/editEvent")
	public String updateEvent(@Valid @ModelAttribute("event") Event event,BindingResult result,HttpSession session) {
		User userTemp = (User) session.getAttribute("userInSession"); //Obj User o null
		if(userTemp == null) {
		return "redirect:/";
		}
		
		if(result.hasErrors()) {
			return "editEvent.jsp";
		}else {
			
			Event thisEvent= serv.findEvent(event.getId());
			List<User> usersJoinedInEvent = thisEvent.getJoinedUsers();
	    	event.setJoinedUsers(usersJoinedInEvent);
	    	
			serv.saveEvent(event);
			
			return "redirect:/dashboard";
		}
		
		
		
		
		
	}
	//-------------------------------------------------
	//-----------------------BORRADO----------------------
	@DeleteMapping("/event/delete/{id}")
	public String borrar(@PathVariable("id") Long idBorrar,HttpSession session,@ModelAttribute("event") Event event) {
		
		User userTemp = (User) session.getAttribute("userInSession"); //Obj User o null
	    if(userTemp == null) {
	            return "redirect:/";
	     }
	    Event eventDeleting= serv.findEvent(idBorrar);
	    if(userTemp.getId() != eventDeleting.getHost().getId()) {
	    	return "redirect:/dashboard";
	    }
	  
		serv.deleteEvent(idBorrar);
		return "redirect:/dashboard";
	}
	
	
	
	//.................
	@PostMapping("/createMessage")
		public String createMessage(@Valid @ModelAttribute("message") Message message,
				BindingResult result,
				HttpSession session,
				Model model) {
	/* === REVISAMOS SESION === */
		User userTemp = (User) session.getAttribute("userInSession"); //Obj User o null
		if(userTemp == null) {
		return "redirect:/";
		}
		/* === REVISAMOS SESION === */
		
		if(result.hasErrors()) {
		model.addAttribute("event", message.getEvent());
		return "event.jsp";
		} else {
		serv.saveMessage(message);
		return "redirect:/event/"+message.getEvent().getId();
		}
	}
		
	
}