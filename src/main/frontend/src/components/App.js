import React from 'react';
import { Switch, Route, BrowserRouter as Router, NavLink } from 'react-router-dom';
import { configureStore } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import { Provider } from 'react-redux';
import { createBrowserHistory } from 'history';
import { routerMiddleware } from 'connected-react-router';
import createRootReducer from '../reducers';
import rootSaga from '../sagas/';
import Home from './Home';
import About from './About';
import Counter from './Counter';


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

function App() {
    return (
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
        </Provider>
    );
}

export default App;
