import { takeLatest, call, put, select } from "redux-saga/effects";
import axios from "axios";
import {
    DECREMENT_REQUEST,
    DECREMENT_RECEIVE,
    DECREMENT_FAILURE,
} from '../actiontypes';

function doDecrement(value) {
    return axios.post('/frontend-karaf-demo/api/increment', { value, delta: -1 });
}

function* sendReceiveDecrement() {
    try {
        const currentValue = yield select(state => state.counter);
        const response = yield call(doDecrement, currentValue);
        const decrementedCounter = response.data;
        yield put(DECREMENT_RECEIVE(decrementedCounter.value));
    } catch (error) {
        yield put(DECREMENT_FAILURE(error));
    }
}

export default function* decrementSaga() {
    yield takeLatest(DECREMENT_REQUEST, sendReceiveDecrement);
}
