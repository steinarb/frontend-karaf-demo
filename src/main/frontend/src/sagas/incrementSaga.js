import { takeLatest, call, put, fork, select } from "redux-saga/effects";
import axios from "axios";
import {
    INCREMENT_REQUEST,
    INCREMENT_RECEIVE,
    INCREMENT_FAILURE,
} from '../actiontypes';

function doIncrement(value) {
    return axios.post('/frontend-karaf-demo/api/increment', { value, delta: 1 });
}

function* sendReceiveIncrement() {
    try {
        const currentValue = yield select(state => state.counter);
        const response = yield call(doIncrement, currentValue);
        const incrementedCounter = response.data;
        yield put(INCREMENT_RECEIVE(incrementedCounter.value));
    } catch (error) {
        yield put(INCREMENT_FAILURE(error));
    }
}

export default function* incrementSaga() {
    yield takeLatest(INCREMENT_REQUEST, sendReceiveIncrement);
}
