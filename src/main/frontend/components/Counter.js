import React, { Component } from 'react';
import { connect } from 'react-redux';
import { fetchIncrement } from '../actions';

let Counter = ({value, onIncrement}) => (
    <p>
        {value}
        <button onClick={() => onIncrement(value, 1)}>+</button>
        <button onClick={() => onIncrement(value, -1)}>-</button>
    </p>
);
Counter = connect(
    (state, ownProps) => ({ value: state.value }),
    (dispatch, ownProps) => ({ onIncrement: (value, delta) => dispatch(fetchIncrement(value, delta)) })
)(Counter);

export default Counter;
