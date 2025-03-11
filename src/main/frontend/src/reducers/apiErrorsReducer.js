import { createReducer } from '@reduxjs/toolkit';
import { api } from '../api';

export default createReducer(0, builder => {
    builder
        .addMatcher(api.endpoints.postIncrement.matchRejected, (state, action) => action.payload)
        .addMatcher(api.endpoints.postDecrement.matchRejected, (state, action) => action.payload);
});
