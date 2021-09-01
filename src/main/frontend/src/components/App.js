import React from 'react';
import { Switch, Route, BrowserRouter as Router, NavLink } from 'react-router-dom';
import { configureStore } from '@reduxjs/toolkit';
import createSagaMiddleware from 'redux-saga';
import { Provider } from 'react-redux';
import { createBrowserHistory } from 'history';
import { routerMiddleware } from 'connected-react-router';
import axios from 'axios';
import createRootReducer from '../reducers';
import rootSaga from '../sagas/';
import Home from './Home';
import About from './About';
import Counter from './Counter';


const baseUrl = Array.from(document.scripts).map(s => s.src).filter(src => src.includes('bundle.js'))[0].replace('/bundle.js', '');
const basename = new URL(baseUrl).pathname;
axios.defaults.baseURL = baseUrl;
const sagaMiddleware = createSagaMiddleware();
const history = createBrowserHistory({ basename });
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
            <Router basename={basename}>
                <div className="App">
                    <div className="container">
                        <NavLink to="/"><button>Home</button></NavLink>
                        <NavLink to="/counter"><button>Counter</button></NavLink>
                        <NavLink to="/about"><button>About</button></NavLink>
                        <hr/>
                    </div>
                    <Switch>
                        <Route exact path="/" component={Home} />
                        <Route path="/counter" component={Counter} />
                        <Route path="/about" component={About} />
                    </Switch>
                </div>
            </Router>
        </Provider>
    );
}

export default App;
