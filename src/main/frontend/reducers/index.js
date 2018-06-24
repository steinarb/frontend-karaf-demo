export const counterReducer = (state = { value: 0 }, action) => {
    if (action.type === 'INCREMENT_REQUEST') {
        return {
            ...state
        };
    }
    if (action.type === 'INCREMENT_RECEIVE') {
        return {
            ...state,
            value: action.value
        };
    }
    return { ...state };
}
