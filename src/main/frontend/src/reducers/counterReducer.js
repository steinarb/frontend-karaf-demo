import { createReducer } from '@reduxjs/toolkit';
import {
    INCREMENT_RECEIVE,
    DECREMENT_RECEIVE,
} from '../reduxactions';

const counterReducer = createReducer(0, {
    [INCREMENT_RECEIVE]: (state, action) => action.payload,
    [DECREMENT_RECEIVE]: (state, action) => action.payload,
});

export default counterReducer;
