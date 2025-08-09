import { configureStore } from '@reduxjs/toolkit';

import AnalyticsSlice from './slices/AnalyticsSlice';
import VisitorCountSlice from './slices/VisitorCountSlice';
import OrderCountSlice from './slices/OrderCountSlice';
import UserCountSlice from './slices/UserCountSlice';

const store = configureStore({
  reducer: {
    AnalyticsSlice,
    VisitorCountSlice,
    OrderCountSlice,
    UserCountSlice
  }
});

export default store;
