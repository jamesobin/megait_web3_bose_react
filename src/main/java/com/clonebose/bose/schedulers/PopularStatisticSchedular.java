package com.clonebose.bose.schedulers;

import com.clonebose.bose.mappers.PopularProductMapper;
import com.clonebose.bose.models.PopularStatistic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class PopularStatisticSchedular {
	@Autowired
	private PopularProductMapper PopularProductMapper;

	// 예시: 인기 상품 ID 리스트 (실제 환경에서는 DB에서 동적으로 조회 필요)
	private final List<Long> popularProductIds = List.of(1L, 2L, 3L, 4L, 5L);
	private final Random random = new Random();

	/**
	 * 5분마다 인기 상품별 판매량을 집계하여 popular_statistic 테이블에 저장/업데이트
	 */
	@Scheduled(fixedRate = 300000) // 5분
	public void generatePopularProductStatistics() {
		for (Long productId : popularProductIds) {
			try {
				// 5분간 랜덤 판매량 증가 (예: 1~10개)
				long soldIncrement = random.nextInt(10) + 1;
				log.info("[인기상품] productId={} 판매량 증가: {}개", productId, soldIncrement);

				int todayCount = PopularProductMapper.getTodayStatisticCount(productId);
				if (todayCount == 0) {
					LocalDateTime now = LocalDateTime.now();
					PopularStatistic statistic = new PopularStatistic();
					statistic.setProductId(productId);
					statistic.setTotalSoldCount(soldIncrement);
					statistic.setRegDate(now);
					statistic.setEditDate(now);
					PopularProductMapper.insertPopularStatistic(statistic);
					log.info("[인기상품] 신규 통계 생성: productId={}, soldCount={}", productId, soldIncrement);
				} else {
					PopularProductMapper.updateTodayStatistic(productId, soldIncrement);
					log.info("[인기상품] 기존 통계 업데이트: productId={}, soldCount+={}", productId, soldIncrement);
				}
			} catch (Exception e) {
				log.error("[인기상품] 통계 집계 중 오류 발생: productId={}", productId, e);
			}
		}
	}
}
