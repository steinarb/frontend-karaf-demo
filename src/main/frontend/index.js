import React from 'react';
import { render } from 'react-dom';
import { Switch, Route, BrowserRouter as Router, NavLink } from 'react-router-dom';
import { applyMiddleware, createStore } from 'redux';
import createSagaMiddleware from 'redux-saga';
import { connect, Provider } from 'react-redux';
import { counterReducer } from './reducers';
import Home from './components/Home';
import About from './components/About';
import Counter from './components/Counter';
import { rootSaga } from './sagas';

const sagaMiddleware = createSagaMiddleware();
const store = createStore(counterReducer, applyMiddleware(sagaMiddleware));
sagaMiddleware.run(rootSaga);

render(
    <Provider store={store}>
        <Router>
            <div className="App">
                <div className="container">
                    <ul>
                        <li><NavLink to="/frontend-karaf-demo/">Home</NavLink></li>
                        <li><NavLink to="/frontend-karaf-demo/counter">Counter</NavLink></li>
                        <li><NavLink to="/frontend-karaf-demo/about">About</NavLink></li>
                    </ul>
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
