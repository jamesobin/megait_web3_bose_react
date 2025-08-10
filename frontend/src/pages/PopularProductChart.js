import React, { memo, useCallback, useEffect, useMemo, useState } from "react";
import styled from "styled-components";
import { useDispatch, useSelector } from "react-redux";
import Spinner from "../components/Spinner";
import { getPopularProducts } from "../slices/PopularProductsSlice";

import {
  Chart,
  CategoryScale,
  LinearScale,
  Title,
  Tooltip,
  Legend,
  BarElement,
} from 'chart.js';

import { Bar } from 'react-chartjs-2';

// Chart.js에서 import한 Chart컴포넌트 내에서 import 요소들을 등록한다.
Chart.register(CategoryScale, LinearScale, Title, Tooltip, Legend, BarElement);

const PopularProductsChartContainer = styled.div`
  padding: 20px;
  flex: 1 1 calc(50% - 20px);
  min-height: 600px; /* 높이 증가 */
  height: 100%;
`;

const ChartContainer = styled.div`
  height: 500px; /* 차트 높이 대폭 증가 */
  width: 100%;
  
  /* 반응형 디자인 */
  @media (max-width: 768px) {
    height: 400px;
  }
`;

const ChartHeader = styled.div`
  display: flex;
  gap: 20px;
  align-items: center;
  margin-bottom: 20px;
`;

const ChartTitle = styled.h2`
  margin: 0;
`;

const StatsSelect = styled.select`
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  font-size: 14px;
  cursor: pointer;

  &:focus {
    outline: none;
    border-color: #007bff;
  }
`;

const PopularProductChart = memo(() => {
  const { loading, status, message, item } = useSelector((state) => state.PopularProductsSlice);
  
  const dispatch = useDispatch();
  
  // 통계 타입 상태 - 기본값을 weekly로 설정
  const [statsType, setStatsType] = useState('weekly');

  useEffect(() => {
    dispatch(getPopularProducts());
  }, [dispatch]);
  
  // 선택된 통계 타입에 따라 차트 데이터 생성
  const chartData = useMemo(() => {
    console.log('🔍 Debug - item data:', item); // 디버깅용
    if (!item) {
      console.log('❌ No item data received');
      return null;
    }

    let data, title, backgroundColor, borderColor;

    switch (statsType) {
      case 'weekly':
        data = item.weeklyStats;
        title = "인기 상품 TOP 8 (일주일 내)";
        backgroundColor = "rgba(255, 99, 132, 0.5)";
        borderColor = "rgba(255, 99, 132, 1)";
        break;
      case 'monthly':
        data = item.monthlyStats;
        title = "인기 상품 TOP 8 (한달 내)";
        backgroundColor = "rgba(54, 162, 235, 0.5)";
        borderColor = "rgba(54, 162, 235, 1)";
        break;
      case 'sixMonth':
        data = item.sixMonthStats;
        title = "인기 상품 TOP 8 (6개월 내)";
        backgroundColor = "rgba(255, 206, 86, 0.5)";
        borderColor = "rgba(255, 206, 86, 1)";
        break;
      case 'yearly':
        data = item.yearlyStats;
        title = "인기 상품 TOP 8 (1년 내)";
        backgroundColor = "rgba(75, 192, 192, 0.5)";
        borderColor = "rgba(75, 192, 192, 1)";
        break;
      case 'threeYear':
        data = item.threeYearStats;
        title = "인기 상품 TOP 8 (3년 내)";
        backgroundColor = "rgba(153, 102, 255, 0.5)";
        borderColor = "rgba(153, 102, 255, 1)";
        break;
      case 'allTime':
        data = item.allTimeStats;
        title = "인기 상품 TOP 8 (전체 기간)";
        backgroundColor = "rgba(255, 159, 64, 0.5)";
        borderColor = "rgba(255, 159, 64, 1)";
        break;
      default:
        return null;
    }

    if (!data) {
      console.log(`❌ No ${statsType} data found`, data);
      return null;
    }

    console.log(`✅ ${statsType} data:`, data); // 디버깅용

    // 상품명 줄임 처리 함수
    const shortenProductName = (name) => {
      if (!name) return name;
      
      // [BOSE] 제거
      let shortened = name.replace(/^\[BOSE\]\s*/, '');
      
      // "보스" 제거 (중복 방지)
      shortened = shortened.replace(/^보스\s*/, '');
      
      // 너무 긴 경우 핵심 단어만 추출
      if (shortened.length > 20) {
        // 주요 키워드 추출
        const keywords = shortened.match(/(QC|울트라|오픈|이어버드|헤드폰|스피커|베이스|서라운드|모듈|플로어|스탠드)/g);
        if (keywords && keywords.length > 0) {
          return keywords.slice(0, 2).join(' '); // 최대 2개 키워드
        }
        // 키워드가 없으면 앞부분만 자르기
        return shortened.substring(0, 18) + '...';
      }
      
      return shortened;
    };

    const labels = Object.keys(data).map(shortenProductName);
    const values = Object.values(data);
    
    console.log('📊 Chart labels:', labels); // 디버깅용
    console.log('📊 Chart values:', values); // 디버깅용

    return {
      chartData: {
        labels: labels,
        datasets: [
          {
            label: "판매량",
            data: values,
            backgroundColor: backgroundColor,
            borderColor: borderColor,
            borderWidth: 1,
          },
        ],
      },
      title: title
    };
  }, [item, statsType]);

  const handleStatsTypeChange = useCallback((e) => {
    setStatsType(e.target.value);
  }, []);

  return (
    <PopularProductsChartContainer>
      <ChartHeader>
        <ChartTitle>인기 상품</ChartTitle>          
        <StatsSelect value={statsType} onChange={handleStatsTypeChange}>
          <option value="weekly">일주일</option>
          <option value="monthly">한달 내</option>
          <option value="sixMonth">6개월 내</option>
          <option value="yearly">1년 내</option>
          <option value="threeYear">3년 내</option>
          <option value="allTime">전체 기간</option>
        </StatsSelect>
      </ChartHeader>
      <Spinner loading={loading} />

      {chartData && (
        <ChartContainer>
          <Bar
            data={chartData.chartData}
            options={{
              responsive: true,
              maintainAspectRatio: false,
              plugins: {
                legend: {
                  position: "bottom",
                  labels: {
                    padding: 20,
                    font: {
                      size: 12
                    }
                  }
                },
                title: {
                  display: true,
                  text: chartData.title,
                  font: {
                    size: 18,
                    color: "#000",
                  },
                  padding: {
                    top: 10,
                    bottom: 30
                  }
                },
              },
              scales: {
                y: {
                  beginAtZero: true,
                  ticks: {
                    stepSize: 1,
                    font: {
                      size: 12
                    }
                  }
                },
                x: {
                  ticks: {
                    maxRotation: 45,
                    minRotation: 30,
                    font: {
                      size: 11
                    },
                    maxTicksLimit: 8 // 최대 8개 라벨만 표시
                  }
                }
              },
              layout: {
                padding: {
                  top: 20,
                  bottom: 20,
                  left: 10,
                  right: 10
                }
              }
            }}
          />
        </ChartContainer>
      )}
    </PopularProductsChartContainer>
  );
});

export default PopularProductChart;
