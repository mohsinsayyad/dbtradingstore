package com.trading.store.dao;

import com.trading.store.model.TradingStoreEntity;

public interface ITradingStoreDao {

	public boolean addTradingStore(TradingStoreEntity store) throws Exception;
	public boolean updateTradingStore(TradingStoreEntity store) throws Exception;
	public boolean updateExpiryFlag();
}
