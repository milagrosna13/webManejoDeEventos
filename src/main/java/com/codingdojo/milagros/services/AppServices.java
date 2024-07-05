package com.codingdojo.milagros.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.milagros.models.Event;
import com.codingdojo.milagros.models.Message;
import com.codingdojo.milagros.models.User;
import com.codingdojo.milagros.repositories.EventRepository;
import com.codingdojo.milagros.repositories.MessageRepository;
import com.codingdojo.milagros.repositories.UserRepository;

@Service
public class AppServices {
	@Autowired
	private UserRepository ur;
	@Autowired
	private EventRepository er;
	@Autowired
	private MessageRepository mr;
	
	/*Método que registre a un nuevo usuario*/
	public User register(User newUser, BindingResult result) {
		
		//Comparar las contraseñas
		String password = newUser.getPassword();
		String confirm = newUser.getConfirm();
		if(!password.equals(confirm)) {
			//SI no son iguales
			//path, clave, mensaje
			result.rejectValue("confirm", "Matches", "Password and confirmation don't match");
		}
		
		//Revisar que el email no esté registrado
		String email = newUser.getEmail();
		User userExist = ur.findByEmail(email); //Objeto de User o null
		if(userExist != null) {
			//El correo ya está registrado
			result.rejectValue("email", "Unique", "E-mail already exists");
		}
		
		//Si existe error, regreso null
		if(result.hasErrors()) {
			return null;
		} else {
			//NO HAY ERRORES
			//Hashear contraseña
			String passHash = BCrypt.hashpw(password, BCrypt.gensalt());
			newUser.setPassword(passHash); //Establecemos el password hasheado
			return ur.save(newUser);
		}
		
	}
	
	/*Método que revisa que los datos sean correctos para Iniciar Sesión*/
	public User login(String email, String password) {
		//Revisamos que el correo exista en BD
		User userTryingLogin = ur.findByEmail(email); //Objeto User o NULL
		
		if(userTryingLogin == null) {
			return null;
		}
		
		//Comparar las contraseñas
		//BCrypt.checkpw(Contra NO encriptada, Contra SI encriptada) -> True o False
		if(BCrypt.checkpw(password, userTryingLogin.getPassword())) {
			return userTryingLogin;
		} else {
			return null;
		}
		
		
	}
	
	
	
	public Event saveEvent(Event newEvent) {
		
		
		return er.save(newEvent);
	}
	
	
	public User getUser(Long id) {
	
		return ur.findById(id).orElse(null);
	}
	
	
	public List<Event> getEventsNear(String province){
		return er.findByEventProvince(province);
		
	}
	
	
	public List<Event> getEventsNotNear(String province){
		return er.findByEventProvinceIsNot(province);
		
	}
	
	public Event getEvent(Long id) {
		return er.findById(id).orElse(null);
	}
	
	public void joinEvent(Long userId, Long eventId) {
		
		User myUser=getUser(userId);
		Event myEvent=getEvent(eventId);
		myEvent.getJoinedUsers().add(myUser);
		er.save(myEvent);
		
		
	}
	
	public void cancelEvent(Long userId, Long eventId) {
		
		User myUser=getUser(userId);
		Event myEvent=getEvent(eventId);
		myEvent.getJoinedUsers().remove(myUser);
		er.save(myEvent);
		
		
	}
	
	public Message saveMessage(Message message) {
		
		return mr.save(message);
		
	}
	

	
	public Event findEvent(Long id) {
		return er.findById(id).orElse(null);
	}
	
	public void deleteEvent(Long id) {
			Event event= er.findById(id).orElse(null);
		  List<Message> messages = event.getEventMessages();
	        
	        // Eliminar cada mensaje
	        for (Message message : messages) {
	            message.setEvent(null); // Desvincular el mensaje del evento
	        }
	        
	        // Limpiar la lista de mensajes del evento
	        messages.clear();
		er.deleteById(id);
	}
	
}
