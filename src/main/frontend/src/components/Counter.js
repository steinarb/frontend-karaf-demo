import React from 'react';
import { connect } from 'react-redux';
import {
    DELTA_MODIFY,
    INCREMENT_REQUEST,
    DECREMENT_REQUEST,
} from '../reduxactions';


function Counter(props) {
    const {delta, counter, onDeltaModify, onIncrement, onDecrement} = props;

    return (
        <div>
            <h1>Counting high and low</h1>
            <p>
                Increment step:
                <input id="delta" type="text" value={delta} onChange={onDeltaModify} />
            </p>
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
        delta: state.delta,
        counter: state.counter,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        onDeltaModify: e => dispatch(DELTA_MODIFY(e.target.value)),
        onIncrement: () => dispatch(INCREMENT_REQUEST()),
        onDecrement: () => dispatch(DECREMENT_REQUEST()),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Counter);
