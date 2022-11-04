package com.trading.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Method;
import java.time.LocalDate;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.trading.store.dao.ITradingStoreDao;
import com.trading.store.dao.TradingStoreDao;
import com.trading.store.model.TradingStoreEntity;
import com.trading.store.repository.TradingStoreRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StoreApplicationDaoTests {

	@Autowired
	ITradingStoreDao tradingStoreDao;
	
	@Autowired
	TradingStoreRepository repository; 

	@Autowired
	private TradingStoreDao app;
	
	@Test
	@Order(1)
	public void validateTradingStoreVersion_Success() throws Exception {
		//mock private methods
		TradingStoreEntity store = new TradingStoreEntity("T1", 2, "CP-1", "B1", LocalDate.of(2023, 5, 20), LocalDate.now(), "N");
		Method method = TradingStoreDao.class.getDeclaredMethod("validateTradingStoreVersion", TradingStoreEntity.class);
		method.setAccessible(true);
		boolean isValidVersion = (boolean) method.invoke(app, store);
		Assert.state(isValidVersion, "Valid Version");
		assertEquals(isValidVersion, true);
	}

	@Test
	@Order(2)
	public void validateTradingStoreVersion_Failure() {
		TradingStoreEntity store = new TradingStoreEntity("T2", 1, "CP-1", "B1", LocalDate.of(2023, 5, 20), LocalDate.now(), "N");
		try {
			//mock private methods
			Method method = TradingStoreDao.class.getDeclaredMethod("validateTradingStoreVersion", TradingStoreEntity.class);
			method.setAccessible(true);
			boolean isValidVersion = (boolean) method.invoke(app, store);
			Assert.state(isValidVersion, "Not Valid Version");
			assertEquals(isValidVersion, false);

		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	public void validateTradingStoreMaturityDate() throws Exception {
		//mock private methods
		TradingStoreEntity store = new TradingStoreEntity("T1", 2, "CP-1", "B1", LocalDate.of(2023, 5, 20), LocalDate.now(), "N");
		Method method = TradingStoreDao.class.getDeclaredMethod("validateTradingStoreMaturityDate", TradingStoreEntity.class);
		method.setAccessible(true);
		boolean isValidDate = (boolean) method.invoke(app, store);
		Assert.state(isValidDate, "Valid Maturity Date");
		assertEquals(isValidDate, true);

	}

	@Test
	public void validateTradingStoreMaturityDate_Invalid() throws Exception {
		//mock private methods
		TradingStoreEntity store = new TradingStoreEntity("T1", 2, "CP-1", "B1", LocalDate.of(2021, 5, 20), LocalDate.now(), "N");
		Method method = TradingStoreDao.class.getDeclaredMethod("validateTradingStoreMaturityDate", TradingStoreEntity.class);
		method.setAccessible(true);
		boolean isValidDate = (boolean) method.invoke(app, store);
		assertEquals(isValidDate, false, "Invalid Maurity Date");

	}

	@Test
	@Order(3)
	public void addTradingStore() throws Exception {
		TradingStoreEntity store = new TradingStoreEntity("T1", 2, "CP-1", "B1", LocalDate.of(2023, 5, 20), LocalDate.now(), "N");
		boolean tradeAdded = tradingStoreDao.addTradingStore(store);
		Assert.state(tradeAdded, "Trade added successfully");
		assertEquals(tradeAdded, true);
	}

	@Test
	@Order(4)
	public void addTradingStoreT5() throws Exception {
		TradingStoreEntity store = new TradingStoreEntity("T5", 1, "CP-1", "B1", LocalDate.of(2023, 5, 20), LocalDate.now(), "N");
		boolean tradeAdded = tradingStoreDao.addTradingStore(store);
		Assert.state(tradeAdded, "Trade added successfully");
		assertEquals(tradeAdded, true);
	}

	@Test
	@Order(5)
	public void addTradingStore_Failure_Version() {
		TradingStoreEntity store = new TradingStoreEntity("T1", 1, "CP-1", "B1", LocalDate.of(2023, 5, 20), LocalDate.now(), "N");
		try {
			boolean tradeAdded = tradingStoreDao.addTradingStore(store);
			Assert.state(tradeAdded, "Trade not added");
			assertEquals(tradeAdded, false);
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	@Order(6)
	public void addTradingStore_Failure_MaturityDate() {
		TradingStoreEntity store = new TradingStoreEntity("T2", 3, "CP-1", "B1", LocalDate.of(2017, 5, 20), LocalDate.now(), "N");
		try {
			boolean tradeAdded = tradingStoreDao.addTradingStore(store);
			Assert.state(tradeAdded, "Trade not added");
			assertEquals(tradeAdded, false);
		} catch (Exception e) {
			assertNotNull(e);
		}
	}

	@Test
	@Order(7)
	public void updateTradingStore() throws Exception {
		TradingStoreEntity store = new TradingStoreEntity("T1", 2, "CP-1", "B1", LocalDate.of(2023, 5, 20), LocalDate.now(), "N");
		boolean tradeUpdated = tradingStoreDao.updateTradingStore(store);
		Assert.state(tradeUpdated, "Trade updated successfully");
		assertEquals(tradeUpdated, true);
	}

	@Test
	@Order(8)
	public void updateExpiryFlag() {
		boolean expiryFlagUpdated = tradingStoreDao.updateExpiryFlag();
		assertEquals(expiryFlagUpdated, true, "Expiry Flag Updated updated successfully");
	}

}
