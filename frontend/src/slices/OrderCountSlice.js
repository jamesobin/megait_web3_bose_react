import reduxHelper from '../helpers/ReduxHelper';

const API_URL = '/api/order/count';

export const getOrderCount = reduxHelper.get("OrderCountSlice/getOrderCount", API_URL);

const OrderCountSlice = reduxHelper.getDefaultSlice("OrderCountSlice", [getOrderCount]);

export default OrderCountSlice.reducer;