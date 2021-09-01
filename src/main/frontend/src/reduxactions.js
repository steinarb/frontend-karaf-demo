import { createAction } from '@reduxjs/toolkit';

export const DELTA_MODIFY = createAction('DELTA_MODIFY');

export const INCREMENT_REQUEST = createAction('INCREMENT_REQUEST');
export const INCREMENT_RECEIVE = createAction('INCREMENT_RECEIVE');
export const INCREMENT_FAILURE = createAction('INCREMENT_FAILURE');

export const DECREMENT_REQUEST = createAction('DECREMENT_REQUEST');
export const DECREMENT_RECEIVE = createAction('DECREMENT_RECEIVE');
export const DECREMENT_FAILURE = createAction('DECREMENT_FAILURE');
