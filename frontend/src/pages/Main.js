import React, { memo } from "react";

import styled from "styled-components";

/** 컴포넌트 참조 */
import Spinner from "../components/Spinner";
import Graph1 from './Graph1';
import Graph2 from './Graph2';
import Graph3 from './Graph3';
import Graph4 from './Graph4';

const PagesContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
`;

const Pages = memo(() => {
  return (
    <PagesContainer>
      <Graph1 />
      <Graph2 />
      <Graph3 />
      <Graph4 />
    </PagesContainer>
  );
});

export default Pages;
