import reduxHelper from '../helpers/ReduxHelper';

const API_URL = '/api/user/count';

export const getUserCount = reduxHelper.get("UserCountSlice/getUserCount", API_URL);

const UserCountSlice = reduxHelper.getDefaultSlice("UserCountSlice", [getUserCount]);

export default UserCountSlice.reducer;