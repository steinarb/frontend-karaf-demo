import { fork, all } from "redux-saga/effects";
import incrementSaga from './incrementSaga';
import decrementSaga from './decrementSaga';

export default function* rootSaga() {
    yield all([
        fork(incrementSaga),
        fork(decrementSaga),
    ]);
};
