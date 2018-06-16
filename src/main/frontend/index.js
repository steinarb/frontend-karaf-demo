import React from 'react';
import { render } from 'react-dom';
import { createStore } from 'redux';
import { Provider } from 'react-redux';

import reducer from './reducers';
import Counter from "./components/Counter.js";

const store = createStore(reducer, { value: 0 });

render(
    <Provider store={store}>
        <Counter />
    </Provider>,
    document.getElementById('root')
);
