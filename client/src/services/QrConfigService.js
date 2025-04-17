
import axiosInterceptor from "src/hooks/interceptor";
export const saveConfig = async (config) => {

    try{
        const response = await axiosInterceptor.post("/api/qr-config/save",config);
        console.log(response);
        return response;
    }catch(e){
        console.log(e);
        const errorMessage =
        e.response?.data?.message || 'Unable to save config';
      throw new Error(errorMessage); // Propagate error to the caller
    
    }

}
