import "core-js/stable";
import "regenerator-runtime/runtime";
import React from 'react';
import { render } from 'react-dom';
import App from './components/App';

render(
    <App />,
    document.getElementById('root')
);
