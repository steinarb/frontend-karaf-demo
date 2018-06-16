const counter = ({ value }, action) => {
    return action.type === 'INCREMENT' ? { value: value + action.delta } : { value };
};

export default counter;
