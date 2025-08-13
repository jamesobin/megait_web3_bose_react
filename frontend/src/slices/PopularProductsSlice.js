import reduxHelper from '../helpers/ReduxHelper';

const API_URL = '/api/popular/products';

export const getPopularProducts = reduxHelper.get("PopularProductsSlice/getPopularProducts", API_URL);

const PopularProductsSlice = reduxHelper.getDefaultSlice("PopularProductsSlice", [getPopularProducts]);

export default PopularProductsSlice.reducer;
