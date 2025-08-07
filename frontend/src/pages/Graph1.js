import React, { memo, useEffect, useMemo } from "react";
import styled from "styled-components";
import { useDispatch, useSelector } from "react-redux";
import {getList} from "../slices/AnalyticsSlice";
import Spinner from "../components/Spinner";

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

const Graph1Container = styled.div`
  background-color: #ff000055;
  padding: 20px;
  flex: 1 1 calc(50% - 20px);
`;

const Graph1 = memo(() => {
  return (
    <Graph1Container>
      <h2>Graph1</h2>
    </Graph1Container>
  );
});

export default Graph1;

