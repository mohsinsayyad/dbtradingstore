package com.trading.store;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.trading.store.model.TradingStoreEntity;
import com.trading.store.repository.TradingStoreRepository;

@DataJpaTest
class StoreApplicationRepositoryTests {

	@Autowired
	TradingStoreRepository repository; 

	@Test
	public void addTradingStore() throws Exception {
		TradingStoreEntity store = new TradingStoreEntity("T1", 2, "CP-1", "B1", LocalDate.of(2023, 5, 20), LocalDate.now(), "N");
		TradingStoreEntity savedTrade = repository.saveAndFlush(store);
		assertThat(savedTrade).isNotNull();
        assertThat(savedTrade.getId()).isGreaterThan(0);
	}
}
