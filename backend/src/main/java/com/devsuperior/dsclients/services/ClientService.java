package com.devsuperior.dsclients.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsclients.dto.ClientDTO;
import com.devsuperior.dsclients.entities.Client;
import com.devsuperior.dsclients.repository.ClientRepository;
import com.devsuperior.dsclients.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	ClientRepository repository;
	
	public List<ClientDTO> findAll(){		
		List<Client> list = repository.findAll();
		List<ClientDTO> dtoList = new ArrayList<>();
		list.stream().map(client -> dtoList.add(new ClientDTO(client))).collect(Collectors.toList());
		return dtoList;
		
	}
	
	public ClientDTO findById(Long id) {
		Optional<Client> opt = repository.findById(id);
		Client client = opt.orElseThrow(() -> new ResourceNotFoundException("Not Found! Id: " + id));
		return new ClientDTO(client);
	}

	public ClientDTO insert(ClientDTO dto) {
		Client entity = new Client();
		clientDtoToClient(dto, entity);
		entity = repository.save(entity);				
		return new ClientDTO(entity);
	}
	
	
	private void clientDtoToClient(ClientDTO dto, Client entity) {		
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());		
		
	}
	
	
	
	
}
