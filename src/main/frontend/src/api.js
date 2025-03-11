import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const api = createApi({
    reducerPath: 'api',
    baseQuery: (...args) => {
        const api = args[1];
        const basename = api.getState().basename;
        return fetchBaseQuery({ baseUrl: basename + '/api' })(...args);
    },
    endpoints: (builder) => ({
        postIncrement: builder.mutation({ query: body => ({ url: '/increment', method: 'POST', body }) }),
        postDecrement: builder.mutation({ query: body => ({ url: '/increment', method: 'POST', body: { ...body, delta: -body.delta } }) }),
    }),
});

console.log(api);

export const {
    usePostIncrementMutation,
    usePostDecrementMutation,
} = api;
