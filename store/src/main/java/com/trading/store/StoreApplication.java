package com.trading.store;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.trading.store.model.TradingStoreEntity;
import com.trading.store.repository.TradingStoreRepository;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class StoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreApplication.class, args);
	}
	
	@Autowired
	TradingStoreRepository tradingStoreRepository;

	@Bean
	public void saveTradingStoresOnStartup() {
		tradingStoreRepository.saveAndFlush(new TradingStoreEntity("T1", 1, "CP-1", "B1", LocalDate.of(2020, 5, 20), LocalDate.now(), "N"));
		tradingStoreRepository.saveAndFlush(new TradingStoreEntity("T2", 2, "CP-2", "B1", LocalDate.of(2021, 5, 20), LocalDate.now(), "N"));
		tradingStoreRepository.saveAndFlush(new TradingStoreEntity("T2", 1, "CP-1", "B1", LocalDate.of(2021, 5, 20), LocalDate.of(2015, 3, 14),"N"));
		tradingStoreRepository.saveAndFlush(new TradingStoreEntity("T3", 3, "CP-3", "B2", LocalDate.of(2014, 5, 20), LocalDate.now(), "Y"));
	}
}
