import React, { Component } from 'react';
import ReactDOM from 'react-dom';

class Counter extends Component {
    state = { value: 0 };

    countUp() {
        this.setState({value: this.state.value + 1});
    }

    countDown() {
        this.setState({value: this.state.value - 1});
    }

    render() {
        return (
            <p>
                {this.state.value}
                <button onClick={() => this.countUp()}>+</button>
                <button onClick={() => this.countDown()}>-</button>
            </p>
        );
    }
}

export default Counter;
