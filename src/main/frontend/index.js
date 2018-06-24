import React from 'react';
import { render } from 'react-dom';
import { applyMiddleware, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga';
import { connect, Provider } from 'react-redux';
import { counterReducer } from './reducers';
import Counter from "./components/Counter.js";
import { rootSaga } from './sagas';

const sagaMiddleware = createSagaMiddleware();
const store = createStore(counterReducer, applyMiddleware(sagaMiddleware));
sagaMiddleware.run(rootSaga);

render(
    <Provider store={store}>
        <div>
            <h1>Counting high and low</h1>
            <Counter />
        </div>
    </Provider>,
    document.getElementById('root')
);
