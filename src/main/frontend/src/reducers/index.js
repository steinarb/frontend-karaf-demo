import { combineReducers } from 'redux';
import { createReducer } from '@reduxjs/toolkit';
import { api } from '../api';
import apiErrors from './apiErrorsReducer';
import delta from './deltaSlice';
import counter from './counterReducer';

export default (routerReducer, basename) => combineReducers({
    router: routerReducer,
    [api.reducerPath]: api.reducer,
    apiErrors,
    delta,
    counter,
    basename: createReducer(basename, (builder) => builder),
});
