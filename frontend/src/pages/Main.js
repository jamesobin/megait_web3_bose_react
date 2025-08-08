import React, { memo } from "react";

import styled from "styled-components";

/** 컴포넌트 참조 */
import Spinner from "../components/Spinner";
import VisitorCountChart from './VisitorCountChart';
import Graph2 from './Graph2';
import OrderCountChart from './OrderCountChart';
import Graph4 from './Graph4';

const PagesContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
`;

const Pages = memo(() => {
  return (
    <PagesContainer>
      <VisitorCountChart />
      <Graph2 />
      <OrderCountChart />
      <Graph4 />
    </PagesContainer>
  );
});

export default Pages;
