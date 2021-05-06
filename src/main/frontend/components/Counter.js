import React, { Component } from 'react';
import { connect } from 'react-redux';

let Counter = ({value, onIncrement}) => (
    <div>
        <h1>Counting high and low</h1>
        <p>
            {value}
            <button onClick={() => onIncrement(value, 1)}>+</button>
            <button onClick={() => onIncrement(value, -1)}>-</button>
        </p>
    </div>
);

const mapStateToProps = state => {
    return {
        value: state.value
    };
};

const mapDispatchToProps = dispatch => {
    return {
        onIncrement: (value, delta) => dispatch({ type: 'INCREMENT_REQUEST', value, delta })
    };
};

Counter = connect(mapStateToProps, mapDispatchToProps)(Counter);

export default Counter;
