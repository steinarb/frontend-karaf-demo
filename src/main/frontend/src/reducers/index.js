import { combineReducers } from 'redux';
import apiErrors from './apiErrorsReducer';
import delta from './deltaReducer';
import counter from './counterReducer';

export default (routerReducer) => combineReducers({
    router: routerReducer,
    apiErrors,
    delta,
    counter,
});
