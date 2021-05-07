import { combineReducers } from 'redux';
import { connectRouter } from 'connected-react-router';
import counter from './counterReducer';

export default (history) => combineReducers({
    router: connectRouter(history),
    counter,
});
