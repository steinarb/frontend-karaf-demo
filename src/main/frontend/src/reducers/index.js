import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router';
import apiErrors from './apiErrorsReducer';
import counter from './counterReducer';

export default (history) => combineReducers({
    router: connectRouter(history),
    apiErrors,
    counter,
});
