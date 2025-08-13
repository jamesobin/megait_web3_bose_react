import React, { memo } from "react";

import styled from "styled-components";

/** 컴포넌트 참조 */
import Spinner from "../components/Spinner";
import VisitorCountChart from './VisitorCountChart';
import UserCountChart from './UserCountChart';
import OrderCountChart from './OrderCountChart';
import PopularProductChart from './PopularProductChart';

const PagesContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
`;

const Pages = memo(() => {
  return (
    <PagesContainer>
      <VisitorCountChart />
      <UserCountChart />
      <OrderCountChart />
      <PopularProductChart />
    </PagesContainer>
  );
});

export default Pages;
