import { createSlice } from '@reduxjs/toolkit';

export const deltaSlice = createSlice({
    name: 'delta',
    initialState: 1,
    reducers: {
        setDelta: (_, action) => parseInt(action.payload) || 0,
    },
});

export const { setDelta } = deltaSlice.actions;
export default deltaSlice.reducer;
