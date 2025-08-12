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

Chart.register(CategoryScale, LinearScale, Title, Tooltip, Legend, BarElement);

const PopularProductsChartContainer = styled.div`
  padding: 20px;
  flex: 1 1 calc(50% - 20px);
  min-height: 600px;
  height: 100%;
`;

const ChartContainer = styled.div`
  height: 300px;
  width: 100%;
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
    if (!item) {
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
      return null;
    }

    // 상품명을 여러 줄로 나누는 함수
    const shortenProductName = (name) => {
      if (!name) return [''];
      
      // [BOSE] 제거
      let shortened = name.replace(/^\[BOSE\]\s*/, '');
      
      // 보스 제거
      shortened = shortened.replace(/^보스\s*/, '');
      
      // 긴 상품명을 여러 줄로 나누기
      const maxLineLength = 12; // 한 줄에 최대 12자
      const words = shortened.split(' ');
      const lines = [];
      let currentLine = '';
      
      for (const word of words) {
        // 현재 줄에 단어를 추가했을 때의 길이 확인
        const testLine = currentLine ? `${currentLine} ${word}` : word;
        
        if (testLine.length <= maxLineLength) {
          currentLine = testLine;
        } else {
          // 현재 줄이 비어있지 않으면 lines에 추가
          if (currentLine) {
            lines.push(currentLine);
          }
          // 새로운 줄 시작
          currentLine = word;
        }
      }
      
      if (currentLine) {
        lines.push(currentLine);
      }
      
      // 최대 2줄로 제한
      const result = lines.slice(0, 2);
      
      // 2줄을 초과하는 경우 마지막 줄에 ... 추가
      if (lines.length > 2) {
        result[1] = result[1].substring(0, 9) + '...';
      }
      
      return result.length > 0 ? result : ['상품명'];
    };

    const labels = Object.keys(data).map(shortenProductName);
    const values = Object.values(data);

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
                    maxRotation: 0, // 회전 없이 수평으로 표시
                    minRotation: 0,
                    font: {
                      size: 10
                    },
                    maxTicksLimit: 8, // 최대 8개 라벨만 표시
                    padding: 5,
                    // 여러 줄 라벨을 위한 콜백 함수
                    callback: function(value, index, ticks) {
                      const label = this.getLabelForValue(value);
                      return Array.isArray(label) ? label : [label];
                    }
                  },
                  // x축과 라벨 사이의 여백 증가
                  grid: {
                    offset: true
                  }
                }
              },
            }}
          />
        </ChartContainer>
      )}
    </PopularProductsChartContainer>
  );
});

export default PopularProductChart;