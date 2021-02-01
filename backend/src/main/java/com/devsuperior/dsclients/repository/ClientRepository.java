package com.devsuperior.dsclients.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dsclients.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	
}