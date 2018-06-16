import { requestIncrement, receiveIncrement } from "../actions";

export const counter = ({ value }, action) => {
    return action.type === 'INCREMENT' ? { value: value + action.delta } : { value };
};

export const counterReducer = (state = { value: 0 }, action) => {
    if (action.type === 'REQUEST_INCREMENT') {
        return {
            ...state
        };
    }
    if (action.type === 'RECEIVE_INCREMENT') {
        return {
            ...state,
            value: action.incrementedCounter.value
        };
    }
    return { ...state };
}
