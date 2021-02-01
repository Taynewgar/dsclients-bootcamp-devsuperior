package com.devsuperior.dsclients.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dsclients.dto.ClientDTO;
import com.devsuperior.dsclients.entities.Client;
import com.devsuperior.dsclients.repository.ClientRepository;
import com.devsuperior.dsclients.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	ClientRepository repository;
	
	private final String RESOURCE_NOT_FOUND_MESSAGE =  "Not Found! Id: ";
	
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
	
	private void clientDtoToClientEntity(ClientDTO dto, Client entity) {		
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());		
		
	}

	
	
	
	
}
