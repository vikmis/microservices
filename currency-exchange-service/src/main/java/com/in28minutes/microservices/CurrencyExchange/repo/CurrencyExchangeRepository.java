package com.in28minutes.microservices.CurrencyExchange.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.in28minutes.microservices.CurrencyExchange.model.CurrencyExchange;

public interface CurrencyExchangeRepository 
extends JpaRepository<CurrencyExchange, Long> {
	
CurrencyExchange findByFromAndTo(String from, String to);


}