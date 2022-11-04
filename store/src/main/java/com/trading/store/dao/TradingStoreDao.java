package com.trading.store.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.trading.store.model.TradingStoreEntity;
import com.trading.store.repository.TradingStoreRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TradingStoreDao implements ITradingStoreDao {

	private static final Logger logger = Logger.getLogger(TradingStoreDao.class.getName());

	@Autowired
	TradingStoreRepository tradingStoreRepository;

	/**
	 * This method adds trade only if version & maturity date is valid.
	 */
	@Override
	public boolean addTradingStore(TradingStoreEntity store) throws Exception {
		boolean validateTradingStoreVersion = validateTradingStoreVersion(store);
		boolean validateTradingStoreMaturityDate = validateTradingStoreMaturityDate(store);
		if (validateTradingStoreMaturityDate) {
			if (validateTradingStoreVersion) {
				logger.info("Trading Store Added :: " + store);
				tradingStoreRepository.saveAndFlush(store);
				return true;
			}
			logger.info("Trading Store aready exists :: " + store);
			return updateTradingStore(store);
		}
		return false;
	}

	/**
	 * This method validates version. if lower version received then exception is
	 * throwned.
	 */
	private boolean validateTradingStoreVersion(TradingStoreEntity store) throws Exception {

		Optional<TradingStoreEntity> tradingStore = Optional
				.ofNullable(tradingStoreRepository.findByTop(store.getTradeId(),  PageRequest.of(0, 1)));

		if (tradingStore.isPresent() && store.getVersion() < tradingStore.get().getVersion()) {
			logger.info("Lower version is being received :: " + store.getVersion() + " Existing version :: "
					+ tradingStore.get().getVersion());
			throw new Exception();
		} else if (tradingStore.isPresent() && store.getVersion() == tradingStore.get().getVersion()) {
			return false;
		}
		return true;
	}

	/**
	 * This method validates maturity date. if not valid then throw exception.
	 */
	private boolean validateTradingStoreMaturityDate(TradingStoreEntity store) {
		if (store.getMaturityDate().isBefore(LocalDate.now())) {
			logger.info("Maturity Date is less than todays date :: " + store.getMaturityDate());
			return false;
		}
		return true;
	}

	/**
	 * This method will update the Trade. True if updated else it returns false.
	 */
	@Override
	public boolean updateTradingStore(TradingStoreEntity store) throws Exception {
		Optional<TradingStoreEntity> tradingStore = Optional
				.ofNullable(tradingStoreRepository.findByTop(store.getTradeId(), PageRequest.of(0, 1)));
		
		if (tradingStore.isPresent()) {
			store.setId(tradingStore.get().getId());
			tradingStoreRepository.saveAndFlush(store);
			logger.info("Update Trading Store with Trade Id :: " + store.getTradeId());
			logger.info("Updated Trading Store :: " + store);
			return true;
		}
		return false;
	}

	/**
	 * Method to update Expiry Flag after every 5 minutes.
	 */
	@Override
	@Scheduled(cron = "0 0/5 * * * *")
	public boolean updateExpiryFlag() {
		Optional<List<TradingStoreEntity>> tradingStore = Optional
				.ofNullable(tradingStoreRepository.fetchTradeStoreByMaturityDateAndExpiry());

		tradingStore.get().stream().map(ts -> {
			ts.setExpired("Y");
			return ts;
		}).forEach(ts -> {
			tradingStoreRepository.saveAndFlush(ts);
			logger.info("Maturity date updated for Trade :: " + tradingStore);
		});
		logger.info("Trades after expiry flag updates :: " + tradingStore);
		return !tradingStore.isEmpty();
	}
}
