import React, { Component } from 'react';
import { connect } from 'react-redux';
import { increment } from '../actions';

let Counter = ({value, onIncrement}) => (
    <p>
        {value}
        <button onClick={() => onIncrement(1)}>+</button>
        <button onClick={() => onIncrement(-1)}>-</button>
    </p>
);
Counter = connect(
    (state, ownProps) => ({ value: state.value }),
    (dispatch, ownProps) => ({ onIncrement: (delta) => dispatch(increment(delta)) })
)(Counter);

export default Counter;
