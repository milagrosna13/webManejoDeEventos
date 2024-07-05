package com.codingdojo.milagros.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.milagros.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event,Long>{
	
	//Select * from events where province = <provinciaenviada>
	List<Event> findByEventProvince( String province);
	
	//select * from events where province  != provinciaenviada
	List<Event> findByEventProvinceIsNot(String province);
	
	
	
}
