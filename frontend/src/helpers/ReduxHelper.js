import {createSlice, createAsyncThunk} from "@reduxjs/toolkit";
import fetchHelper from "./FetchHelper";

const pending = (state, {meta, payload}) => {
    return {...state, loading: true};
};

const fulfilled = (state, {meta, payload}) => {
    return {...payload, loading: false};
};

const rejected = (state, {meta, payload}) => {
    return {
        ...state,
        loading: false,
        status: payload.status || 0,
        message: payload.message || "unknown Error",
    };
};

const reduxHelper = {
    getDefaultSlice: (slideName, extraReducers = [], callback = {}, reducers = {}) => {
        return createSlice ({
            name: slideName,
            
            initialState: {
                status: 200,
                message: "OK",
                item: null,
                timstamp: null,
                loading: false,
            },

            reducers: reducers,            
            extraReducers: (builder) => {
                extraReducers.forEach((v, i) => {
                    // v가 undefined이거나 필요한 속성이 없는 경우 건너뛰기
                    if (v && v.pending && v.fulfilled && v.rejected) {
                        builder.addCase(v.pending, pending)
                                .addCase(v.fulfilled, callback[v.fulfilled] || fulfilled)
                                .addCase(v.rejected, rejected);
                    }
                });
            },
        });
    },    
    
    get: (alias, url, callback = (payload) => {return {url: url, params: payload}}) => {
        return createAsyncThunk(alias, async (payload, {rejectWithValue}) => {
            let result = null;
            const {url, params} = callback(payload);

            try {
                result = await fetchHelper.get(url, params);
            } catch (err) {
                result = rejectWithValue(err);
            }

            return result;
        });
    },    

    post: (alias, url, callback = (payload) => {return {url: url, params: payload}}) => {
        return createAsyncThunk(alias, async (payload, {rejectWithValue}) => {
            let result = null;
            const {url, params} = callback(payload);

            try {
                result = await fetchHelper.post(url, params);
            } catch (err) {
                result = rejectWithValue(err);
            }

            return result;
        });
    },    

    put: (alias, url, callback = (payload) => {return {url: url, params: payload}}) => {
        return createAsyncThunk(alias, async (payload, {rejectWithValue}) => {
            let result = null;
            const {url, params} = callback(payload);

            try {
                result = await fetchHelper.put(url, params);
            } catch (err) {
                result = rejectWithValue(err);
            }

            return result;
        });
    },    
    
    delete: (alias, url, callback = (payload) => {return {url: url, params: payload}}) => {
        return createAsyncThunk(alias, async (payload, {rejectWithValue}) => {
            let result = null;
            const {url, params} = callback(payload);

            try {
                result = await fetchHelper.delete(url, params);
            } catch (err) {
                result = rejectWithValue(err);
            }

            return result;
        });
    },
};

export default reduxHelper; 