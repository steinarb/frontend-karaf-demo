import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { DELTA_MODIFY } from '../reduxactions';
import { usePostIncrementMutation, usePostDecrementMutation } from '../api';

export default function Counter() {
    const delta = useSelector(state => state.delta);
    const value = useSelector(state => state.counter);
    const dispatch = useDispatch();
    const [ postIncrement ] = usePostIncrementMutation();
    const onPlusButtonClicked = async () => await postIncrement({ value, delta });
    const [ postDecrement ] = usePostDecrementMutation();
    const onMinusButtonClicked = async () => await postDecrement({ value, delta });

    return (
        <div>
            <h1>Counting high and low</h1>
            <p>
                Increment step:
                <input id="delta" type="text" value={delta} onChange={e => dispatch(DELTA_MODIFY(e.target.value))} />
            </p>
            <p>
                {value}
                <button onClick={onPlusButtonClicked}>+</button>
                <button onClick={onMinusButtonClicked}>-</button>
            </p>
        </div>
    );
}
