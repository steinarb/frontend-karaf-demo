import fetch from "isomorphic-fetch";
export const increment = (delta) => ({ type: 'INCREMENT', delta });

export const requestIncrement = () => ({
  type: 'REQUEST_INCREMENT'
});

export const receiveIncrement = incrementedCounter => ({
  type: 'RECEIVE_INCREMENT',
  incrementedCounter
});


export const fetchIncrement = (value, delta) => dispatch => {
  dispatch(requestIncrement());
    fetch('/frontend-karaf-demo/api/increment', {
        body: JSON.stringify({ value, delta }),
        method: 'POST',
        credentials: 'same-origin',
        headers: {
            'content-type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(incrementedCounter => dispatch(receiveIncrement(incrementedCounter)));
};
