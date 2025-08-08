import { configureStore } from '@reduxjs/toolkit';

import AnalyticsSlice from './slices/AnalyticsSlice';
import VisitorCountSlice from './slices/VisitorCountSlice';
import OrderCountSlice from './slices/OrderCountSlice';

const store = configureStore({
  reducer: {
    AnalyticsSlice,
    VisitorCountSlice,
    OrderCountSlice
  }
});

export default store;
