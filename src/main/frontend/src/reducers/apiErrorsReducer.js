import { createReducer } from '@reduxjs/toolkit';
import {
    INCREMENT_FAILURE,
    DECREMENT_FAILURE,
} from '../actiontypes';

const apiErrorsReducer = createReducer(0, {
    [INCREMENT_FAILURE]: (state, action) => ({ ...state, increment: action.payload }),
    [DECREMENT_FAILURE]: (state, action) => ({ ...state, decrement: action.payload }),
});

export default apiErrorsReducer;
