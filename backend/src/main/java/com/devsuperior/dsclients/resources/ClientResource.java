package com.devsuperior.dsclients.resources;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dsclients.entities.Client;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {
	
	@GetMapping
	public ResponseEntity<List<Client>> findAll(){
		
		List<Client> list = new ArrayList<Client>();
		
		list.add(new Client(1L, "Maria", "10099009900", 1567.9, Instant.now(),1));
		list.add(new Client(2L, "João", "1009906666900", 567.9, Instant.now(),4));
		
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")	
	public ResponseEntity<Client> findById(@PathVariable Long id){
		return ResponseEntity.ok().body(new Client(id, "Maria", "10099009900", 1567.9, Instant.now(),1));
	}
	
	
	

}
