package com.clonebose.bose.mappers;

import com.clonebose.bose.models.ProductWithSalesDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application.properties")
@DisplayName("PopularProductMapper 테스트")
public class PopularProductMapperTest {

    @Autowired
    private PopularProductMapper popularProductMapper;

    @Test
    @DisplayName("모든 인기 상품 통계와 상품명 조회 테스트")
    void getAllPopularStatisticsWithProductNameTest() {
        // When
        List<ProductWithSalesDto> results = popularProductMapper.getAllPopularStatisticsWithProductName();

        // Then
        assertThat(results).isNotNull();
        
        // 데이터가 있는 경우 검증
        if (!results.isEmpty()) {
            ProductWithSalesDto firstResult = results.get(0);
            
            // 필수 필드 존재 확인
            assertThat(firstResult.getProductId()).isNotNull();
            assertThat(firstResult.getProductName()).isNotNull();
            assertThat(firstResult.getTotalSoldCount()).isNotNull();
            
            // 상품명이 실제 상품명으로 조회되는지 확인 (product_1 형태가 아닌)
            assertThat(firstResult.getProductName()).doesNotStartWith("product_");
            
            // 판매량이 0 이상인지 확인
            assertThat(firstResult.getTotalSoldCount()).isGreaterThanOrEqualTo(0L);
            
            System.out.println("조회된 인기 상품 수: " + results.size());
            System.out.println("첫 번째 상품: " + firstResult.getProductName() + " (판매량: " + firstResult.getTotalSoldCount() + ")");
        }
    }

    @Test
    @DisplayName("조회 결과 정렬 검증 테스트")
    void sortingValidationTest() {
        // When
        List<ProductWithSalesDto> results = popularProductMapper.getAllPopularStatisticsWithProductName();

        // Then
        if (results.size() > 1) {
            // 판매량이 내림차순으로 정렬되어 있는지 확인
            for (int i = 0; i < results.size() - 1; i++) {
                ProductWithSalesDto current = results.get(i);
                ProductWithSalesDto next = results.get(i + 1);
                
                assertThat(current.getTotalSoldCount())
                    .isGreaterThanOrEqualTo(next.getTotalSoldCount());
            }
            
            System.out.println("정렬 검증 완료: 첫 번째 상품 판매량(" + results.get(0).getTotalSoldCount() + 
                             ") >= 마지막 상품 판매량(" + results.get(results.size() - 1).getTotalSoldCount() + ")");
        }
    }

    @Test
    @DisplayName("LEFT JOIN 정상 동작 확인 테스트")
    void leftJoinValidationTest() {
        // When
        List<ProductWithSalesDto> results = popularProductMapper.getAllPopularStatisticsWithProductName();

        // Then
        for (ProductWithSalesDto result : results) {
            // popular_statistic 테이블의 데이터는 반드시 있어야 함
            assertThat(result.getProductId()).isNotNull();
            assertThat(result.getTotalSoldCount()).isNotNull();
            
            // LEFT JOIN이므로 products 테이블에 없는 상품도 조회될 수 있음
            // 하지만 productName이 null이면 안됨 (COALESCE 또는 IFNULL 사용)
            assertThat(result.getProductName()).isNotNull();
            assertThat(result.getProductName()).isNotEmpty();
        }
    }

    @Test
    @DisplayName("날짜 필드 존재 확인 테스트")
    void dateFieldsValidationTest() {
        // When
        List<ProductWithSalesDto> results = popularProductMapper.getAllPopularStatisticsWithProductName();

        // Then
        if (!results.isEmpty()) {
            ProductWithSalesDto firstResult = results.get(0);
            
            // regDate는 반드시 있어야 함 (popular_statistic 테이블 필수 필드)
            assertThat(firstResult.getRegDate()).isNotNull();
            
            // editDate는 있을 수도 없을 수도 있음
            System.out.println("RegDate: " + firstResult.getRegDate());
            System.out.println("EditDate: " + firstResult.getEditDate());
        }
    }

    @Test
    @DisplayName("데이터베이스 연결 및 쿼리 실행 테스트")
    void databaseConnectionTest() {
        // When & Then
        assertThat(popularProductMapper).isNotNull();
        
        // 예외가 발생하지 않고 정상 실행되는지 확인
        List<ProductWithSalesDto> results = popularProductMapper.getAllPopularStatisticsWithProductName();
        
        // 결과가 null이 아닌지 확인 (빈 리스트일 수는 있음)
        assertThat(results).isNotNull();
        
        System.out.println("데이터베이스 연결 및 쿼리 실행 성공");
        System.out.println("조회된 총 레코드 수: " + results.size());
    }

    @Test
    @DisplayName("SQL 매핑 정확성 테스트")
    void sqlMappingAccuracyTest() {
        // When
        List<ProductWithSalesDto> results = popularProductMapper.getAllPopularStatisticsWithProductName();

        // Then
        for (ProductWithSalesDto result : results) {
            // ProductWithSalesDto의 모든 필드가 올바르게 매핑되었는지 확인
            assertThat(result.getProductId()).isNotNull();
            assertThat(result.getProductName()).isNotNull();
            assertThat(result.getTotalSoldCount()).isNotNull();
            assertThat(result.getRegDate()).isNotNull();
            
            // 데이터 타입 검증
            assertThat(result.getProductId()).isInstanceOf(Long.class);
            assertThat(result.getProductName()).isInstanceOf(String.class);
            assertThat(result.getTotalSoldCount()).isInstanceOf(Long.class);
            
            // 논리적 검증
            assertThat(result.getProductId()).isPositive();
            assertThat(result.getTotalSoldCount()).isGreaterThanOrEqualTo(0L);
            assertThat(result.getProductName().length()).isGreaterThan(0);
        }
    }

    @Test
    @DisplayName("성능 테스트 - 쿼리 실행 시간 측정")
    void performanceTest() {
        // Given
        long startTime = System.currentTimeMillis();

        // When
        List<ProductWithSalesDto> results = popularProductMapper.getAllPopularStatisticsWithProductName();

        // Then
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("쿼리 실행 시간: " + executionTime + "ms");
        System.out.println("조회된 레코드 수: " + results.size());
        
        // 일반적으로 1초 이내에 완료되어야 함 (개발 환경 기준)
        assertThat(executionTime).isLessThan(5000L); // 5초 이내
        
        // 결과 검증
        assertThat(results).isNotNull();
    }
}
