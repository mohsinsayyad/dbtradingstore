package com.trading.store.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trading.store.model.TradingStoreEntity;

@Repository
public interface TradingStoreRepository extends JpaRepository<TradingStoreEntity, Long>{

	@Query("SELECT ts FROM TradingStoreEntity ts WHERE ts.tradeId=?1 ORDER BY ts.version DESC")
	TradingStoreEntity findByTop(String tradeId, PageRequest pageRequest);

	@Query("SELECT ts FROM TradingStoreEntity ts WHERE ts.maturityDate < CURRENT_DATE AND ts.expired='N'")
	List<TradingStoreEntity> fetchTradeStoreByMaturityDateAndExpiry();

	
}
