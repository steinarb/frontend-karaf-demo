import React from 'react';
import { render } from 'react-dom';
import { applyMiddleware, createStore } from 'redux';
import { connect, Provider } from 'react-redux';
import thunk from 'redux-thunk';

import { counterReducer } from './reducers';
import Counter from "./components/Counter.js";

const store = createStore(counterReducer, applyMiddleware(thunk));

render(
    <Provider store={store}>
        <div>
            <h1>Counting high and low</h1>
            <Counter />
        </div>
    </Provider>,
    document.getElementById('root')
);
