import "core-js/stable";
import "regenerator-runtime/runtime";
import React from 'react';
import { render } from 'react-dom';
import { Switch, Route, BrowserRouter as Router, NavLink } from 'react-router-dom';
import { applyMiddleware, createStore, compose } from 'redux';
import { configureStore } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import { connect, Provider } from 'react-redux';
import { createBrowserHistory } from 'history';
import { routerMiddleware } from 'connected-react-router';
import createRootReducer from './reducers';
import rootSaga from './sagas/';
import Home from './components/Home';
import About from './components/About';
import Counter from './components/Counter';


const sagaMiddleware = createSagaMiddleware();
const history = createBrowserHistory();
const store = configureStore({
    reducer: createRootReducer(history),
    middleware: [
        sagaMiddleware,
        routerMiddleware(history),
    ],
});
sagaMiddleware.run(rootSaga);

render(
    <Provider store={store}>
        <Router>
            <div className="App">
                <div className="container">
                    <NavLink to="/frontend-karaf-demo/"><button>Home</button></NavLink>
                    <NavLink to="/frontend-karaf-demo/counter"><button>Counter</button></NavLink>
                    <NavLink to="/frontend-karaf-demo/about"><button>About</button></NavLink>
                    <hr/>
                </div>
                <Switch>
                    <Route exact path="/frontend-karaf-demo/" component={Home} />
                    <Route path="/frontend-karaf-demo/counter" component={Counter} />
                    <Route path="/frontend-karaf-demo/about" component={About} />
                </Switch>
            </div>
        </Router>
    </Provider>,
    document.getElementById('root')
);
