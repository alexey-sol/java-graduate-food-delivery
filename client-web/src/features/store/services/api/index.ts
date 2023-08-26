import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { appConfig } from "app/app-config";
import { transformGetItemsArg } from "shared/utils/converters";
import type { StorePage } from "features/store/models";

import { createTag, baseUrl } from "./utils";
import * as cn from "./const";
import type * as tp from "./types";

const { storeResource } = appConfig;

export const storeApi = createApi({
    reducerPath: "storeApi",
    tagTypes: [cn.STORE_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getStores: builder.query<StorePage, tp.GetStoresArg>({
            query: (arg) => ({
                params: transformGetItemsArg(arg),
                url: storeResource,
            }),
            providesTags: (result) => {
                const tag = createTag();

                return result
                    ? [...result.content.map(({ id }) => ({ type: tag.type, id })), tag]
                    : [tag];
            },
        }),
    }),
});

export const {
    useGetStoresQuery,
} = storeApi;
