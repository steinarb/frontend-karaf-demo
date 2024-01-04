import { createReducer } from '@reduxjs/toolkit';
import {
    INCREMENT_FAILURE,
    DECREMENT_FAILURE,
} from '../reduxactions';

const apiErrorsReducer = createReducer(0, builder => {
    builder
        .addCase(INCREMENT_FAILURE, (state, action) => ({ ...state, increment: action.payload }))
        .addCase(DECREMENT_FAILURE, (state, action) => ({ ...state, decrement: action.payload }));
});

export default apiErrorsReducer;
