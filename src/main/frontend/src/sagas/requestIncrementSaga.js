import { takeLatest, call, put, fork } from "redux-saga/effects";
import axios from "axios";

// watcher saga
export function* requestIncrementSaga() {
    yield takeLatest("INCREMENT_REQUEST", receiveIncrementSaga);
}

function doIncrement(value, delta) {
    return axios.post('/frontend-karaf-demo/api/increment', { value, delta });
}

// worker saga
function* receiveIncrementSaga(action) {
    try {
        const response = yield call(doIncrement, action.value, action.delta);
        const incrementedCounter = response.data;
        yield put({ type: 'INCREMENT_RECEIVE', value: incrementedCounter.value });
    } catch (error) {
        yield put({ type: 'INCREMENT_FAILURE', error });
    }
}

export function* rootSaga() {
    yield [
        fork(requestIncrementSaga),
    ];
};
