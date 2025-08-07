import { configureStore } from '@reduxjs/toolkit';

import AnalyticsSlice from './slices/AnalyticsSlice';

const store = configureStore({
  reducer: {
    AnalyticsSlice
  }
});

export default store;
