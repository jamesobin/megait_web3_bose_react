import React, { memo, useCallback, useEffect, useMemo, useState } from "react";
import styled from "styled-components";
import { useDispatch, useSelector } from "react-redux";
import Spinner from "../components/Spinner";
import { getUserCount } from "../slices/UserCountSlice";

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

const UserCountChartContainer = styled.div`
  padding: 20px;
  flex: 1 1 calc(50% - 20px);
  min-height: 400px;
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

const UserCountChart = memo(() => {
  const { loading, status, message, item } = useSelector((state) => state.UserCountSlice);

  const dispatch = useDispatch();

  // 통계 타입 상태 - 기본값을 daily로 변경
  const [statsType, setStatsType] = useState('daily');

  useEffect(() => {
    dispatch(getUserCount());
  }, [dispatch]);

  // 선택된 통계 타입에 따라 차트 데이터 생성
  const chartData = useMemo(() => {
    if (!item) return null;

    let data, title, backgroundColor, borderColor;

    switch (statsType) {
      case 'daily':
        data = item.dailyStats;
        title = "최근 7일 신규 회원 통계";
        backgroundColor = "rgba(0, 102, 39, 0.5)";
        borderColor = "rgba(0, 102, 39, 0.5)";
        break;
      case 'monthly':
        data = item.monthlyStats;
        title = "월별 신규 회원 통계 (최근 1년)";
        backgroundColor = "rgba(252, 255, 53, 0.5)";
        borderColor = "rgba(252, 255, 53, 1)";
        break;
      case 'yearly':
        data = item.yearlyStats;
        title = "연별 신규 회원 통계";
        backgroundColor = "rgba(255, 80, 80, 0.5)"; 
        borderColor = "rgba(255, 80, 80, 1)";
        break;
      default:
        return null;
    }

    if (!data) return null;

    const keys = Object.keys(data);
    const values = Object.values(data);

    return {
      chartData: {
        labels: keys,
        datasets: [
          {
            label: "신규 회원 수",
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
    <UserCountChartContainer>
      <ChartHeader>
        <ChartTitle>신규 회원 수</ChartTitle>
        <StatsSelect value={statsType} onChange={handleStatsTypeChange}>
          <option value="daily">최근 7일</option>
          <option value="monthly">월별</option>
          <option value="yearly">연별</option>
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
                  },
                },
              },
            }}
          />
        </ChartContainer>
      )}
    </UserCountChartContainer>
  );
});

export default UserCountChart;