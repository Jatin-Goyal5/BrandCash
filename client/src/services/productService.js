
import axiosInterceptor from "src/hooks/interceptor";
export const saveProduct = async (product) => {

    try{
        const response = await axiosInterceptor.post("/product/save",product);
        return response;
    }catch(e){
        const errorMessage =
        error.response?.data?.message || 'Unable to add product';
      console.error(errorMessage);
      throw new Error(errorMessage); // Propagate error to the caller
    
    }

}

export const getProducts = async (product) => {

    try{
        const response = await axiosInterceptor.get("/product/get");
        return response;
    }catch(e){
        console.log(e);
        return null;
    }

}