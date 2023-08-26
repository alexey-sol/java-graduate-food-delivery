import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { appConfig } from "app/app-config";
import { transformGetItemsArg } from "shared/utils/converters";
import type { ProductPage } from "features/product/models";

import { createTag, baseUrl } from "./utils";
import * as cn from "./const";
import type * as tp from "./types";

const { productResource } = appConfig;

export const productApi = createApi({
    reducerPath: "productApi",
    tagTypes: [cn.PRODUCT_TAG_TYPE],
    baseQuery: fetchBaseQuery({ baseUrl }),
    endpoints: (builder) => ({
        getProducts: builder.query<ProductPage, tp.GetProductsArg>({
            query: (arg) => ({
                params: transformGetItemsArg(arg),
                url: productResource,
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
    useGetProductsQuery,
} = productApi;