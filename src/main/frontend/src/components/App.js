import React from 'react';
import { Routes, Route, NavLink, BrowserRouter as Router } from 'react-router';
import { configureStore, Tuple } from '@reduxjs/toolkit';
import { Provider } from 'react-redux';
import createRootReducer from '../reducers';
import { api } from '../api';
import Home from './Home';
import About from './About';
import Counter from './Counter';

const baseUrl = Array.from(document.scripts).map(s => s.src).filter(src => src.includes('assets/'))[0].replace(/\/assets\/.*/, '');
const basename = new URL(baseUrl).pathname.replace(/\/$/, '');
const store = configureStore({
    reducer: createRootReducer(basename),
    middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(api.middleware),
});

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
