import { takeLatest, call, put, select } from "redux-saga/effects";
import axios from "axios";
import {
    DECREMENT_REQUEST,
    DECREMENT_RECEIVE,
    DECREMENT_FAILURE,
} from '../reduxactions';

function doDecrement(value, delta) {
    return axios.post('/api/increment', { value, delta: -delta });
}

function* sendReceiveDecrement() {
    try {
        const delta = yield select(state => state.delta);
        const currentValue = yield select(state => state.counter);
        const response = yield call(doDecrement, currentValue, delta);
        const decrementedCounter = response.data;
        yield put(DECREMENT_RECEIVE(decrementedCounter.value));
    } catch (error) {
        yield put(DECREMENT_FAILURE(error));
    }
}

export default function* decrementSaga() {
    yield takeLatest(DECREMENT_REQUEST, sendReceiveDecrement);
}
