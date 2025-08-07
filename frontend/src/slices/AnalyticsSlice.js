import reduxHelper from '../helpers/ReduxHelper';

const API_URL = '/analytics';

export const getList = reduxHelper.get("AnalyticsSlice/getList", API_URL);

const AnalyticsSlice = reduxHelper.getDefaultSlice("AnalyticsSlice", [getList]);

export default AnalyticsSlice.reducer;
