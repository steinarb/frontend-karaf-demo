import { takeLatest, call, put, select } from "redux-saga/effects";
import axios from "axios";
import {
    INCREMENT_REQUEST,
    INCREMENT_RECEIVE,
    INCREMENT_FAILURE,
} from '../reduxactions';

function doIncrement(value, delta) {
    return axios.post('/api/increment', { value, delta });
}

function* sendReceiveIncrement() {
    try {
        const delta = yield select(state => state.delta);
        const currentValue = yield select(state => state.counter);
        const response = yield call(doIncrement, currentValue, delta);
        const incrementedCounter = response.data;
        yield put(INCREMENT_RECEIVE(incrementedCounter.value));
    } catch (error) {
        yield put(INCREMENT_FAILURE(error));
    }
}

export default function* incrementSaga() {
    yield takeLatest(INCREMENT_REQUEST, sendReceiveIncrement);
}
