import { AddProductView } from "src/sections/add-product/view";
import { Helmet } from 'react-helmet-async';

export default function AddProductPage(){
    return (
        <>
          <Helmet>
            <title> Add Product </title>
          </Helmet>
    
          <AddProductView />
        </>
      );
}