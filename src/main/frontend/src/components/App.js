import React from 'react';
import { Routes, Route, NavLink } from 'react-router-dom';
import { HistoryRouter as Router } from "redux-first-history/rr6";
import { configureStore, Tuple } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import { Provider } from 'react-redux';
import { createReduxHistoryContext } from "redux-first-history";
import { createBrowserHistory } from 'history';
import axios from 'axios';
import createRootReducer from '../reducers';
import rootSaga from '../sagas/';
import Home from './Home';
import About from './About';
import Counter from './Counter';

const baseUrl = Array.from(document.scripts).map(s => s.src).filter(src => src.includes('assets/'))[0].replace(/\/assets\/.*/, '');
const basename = new URL(baseUrl).pathname;
axios.defaults.baseURL = baseUrl;
const sagaMiddleware = createSagaMiddleware();
const {
    createReduxHistory,
    routerMiddleware,
    routerReducer
} = createReduxHistoryContext({ history: createBrowserHistory(), basename });
const store = configureStore({
    reducer: createRootReducer(routerReducer),
    middleware: () => new Tuple(sagaMiddleware, routerMiddleware),
});
sagaMiddleware.run(rootSaga);
const history = createReduxHistory(store);

function App() {
    return (
        <Provider store={store}>
            <Router history={history} basename={basename}>
                <div className="App">
                    <div className="container">
                        <NavLink to="/"><button>Home</button></NavLink>
                        <NavLink to="/counter"><button>Counter</button></NavLink>
                        <NavLink to="/about"><button>About</button></NavLink>
                        <hr/>
                    </div>
                    <Routes>
                        <Route exact path="/" element={<Home/>} />
                        <Route path="/counter" element={<Counter/>} />
                        <Route path="/about" element={<About/>} />
                    </Routes>
                </div>
            </Router>
        </Provider>
    );
}

export default App;
