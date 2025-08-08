import { configureStore } from '@reduxjs/toolkit';

import AnalyticsSlice from './slices/AnalyticsSlice';
import VisitorCountSlice from './slices/VisitorCountSlice';

const store = configureStore({
  reducer: {
    AnalyticsSlice,
    VisitorCountSlice
  }
});

export default store;
