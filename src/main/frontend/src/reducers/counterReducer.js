import { createReducer } from '@reduxjs/toolkit';
import { api } from '../api';

export default createReducer(0, builder => {
    builder
        .addMatcher(api.endpoints.postIncrement.matchFulfilled, (state, action) => action.payload.value)
        .addMatcher(api.endpoints.postDecrement.matchFulfilled, (state, action) => action.payload.value);
});
