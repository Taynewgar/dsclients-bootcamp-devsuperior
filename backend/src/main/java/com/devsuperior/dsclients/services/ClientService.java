package com.devsuperior.dsclients.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsclients.dto.ClientDTO;
import com.devsuperior.dsclients.entities.Client;
import com.devsuperior.dsclients.repository.ClientRepository;
import com.devsuperior.dsclients.services.exceptions.DatabaseException;
import com.devsuperior.dsclients.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	private static final String DATABASE_EXCEPTION_MESSAGE = "Integrity Violation";
	private static final String RESOURCE_NOT_FOUND_MESSAGE =  "Not Found! Id: ";
	
	@Autowired
	ClientRepository repository;
		
	@Transactional(readOnly = true)
	public List<ClientDTO> findAll(){		
		List<Client> list = repository.findAll();
		List<ClientDTO> dtoList = new ArrayList<>();
		list.stream().map(client -> dtoList.add(new ClientDTO(client))).collect(Collectors.toList());
		return dtoList;
		
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> opt = repository.findById(id);
		Client client = opt.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + id));
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		clientDtoToClientEntity(dto, entity);
		entity = repository.save(entity);				
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {
			Client entity = repository.getOne(id);
			clientDtoToClientEntity(dto, entity);
			entity = repository.save(entity);			
			return new ClientDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + id);
		}
		
	}
	
	// I've opted for throwing an exception even though it will hardly occur, once there is no associated class that would
	// throw an integrity violation if deleted. 
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_MESSAGE + id);		
		}
		catch (DataIntegrityViolationException e) {			
			throw new DatabaseException(DATABASE_EXCEPTION_MESSAGE);		
		}			
	}
	
	private void clientDtoToClientEntity(ClientDTO dto, Client entity) {		
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());		
		
	}

	

	
	
	
	
}
