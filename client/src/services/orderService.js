
import axiosInterceptor from "src/hooks/interceptor";
export const createOrder = async (order) => {

    try{
        const response = await axiosInterceptor.post("/api/orders/place",order);
        return response;
    }catch(e){
        const errorMessage =
        error.response?.data?.message || 'Unable to save order contact support';
      console.error(errorMessage);
      throw new Error(errorMessage); // Propagate error to the caller
    
    }

}

export const getOrders = async (product) => {

    try{
        const response = await axiosInterceptor.get("/api/orders/list");
        return response;
    }catch(e){
        console.log(e);
        return null;
    }

}