import React, { Component } from 'react';
import { connect } from 'react-redux';
import {
    INCREMENT_REQUEST,
    DECREMENT_REQUEST,
} from '../actiontypes';


function Counter(props) {
    const {counter, onIncrement, onDecrement} = props;

    return (
        <div>
            <h1>Counting high and low</h1>
            <p>
                {counter}
                <button onClick={onIncrement}>+</button>
                <button onClick={onDecrement}>-</button>
            </p>
        </div>
    );
}

function mapStateToProps(state) {
    return {
        counter: state.counter,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        onIncrement: () => dispatch(INCREMENT_REQUEST()),
        onDecrement: () => dispatch(DECREMENT_REQUEST()),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Counter);
