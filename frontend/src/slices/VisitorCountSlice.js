import reduxHelper from '../helpers/ReduxHelper';

const API_URL = '/api/visitor/count';

export const getVisitorCount = reduxHelper.get("VisitorCountSlice/getVisitorCount", API_URL);

const VisitorCountSlice = reduxHelper.getDefaultSlice("VisitorCountSlice", [getVisitorCount]);

export default VisitorCountSlice.reducer;