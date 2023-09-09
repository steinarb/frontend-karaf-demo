import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
    DELTA_MODIFY,
    INCREMENT_REQUEST,
    DECREMENT_REQUEST,
} from '../reduxactions';


export default function Counter() {
    const delta = useSelector(state => state.delta);
    const counter = useSelector(state => state.counter);
    const dispatch = useDispatch();

    return (
        <div>
            <h1>Counting high and low</h1>
            <p>
                Increment step:
                <input id="delta" type="text" value={delta} onChange={e => dispatch(DELTA_MODIFY(e.target.value))} />
            </p>
            <p>
                {counter}
                <button onClick={() => dispatch(INCREMENT_REQUEST())}>+</button>
                <button onClick={() => dispatch(DECREMENT_REQUEST())}>-</button>
            </p>
        </div>
    );
}
