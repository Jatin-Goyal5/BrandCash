import axios from "axios";

 const axiosInterceptor = axios.create({
    baseURL: "http://localhost:5000/",
    withCredentials:true
})

axiosInterceptor.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem('token');
  
      // If token exists, add it to the Authorization header
      if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
      }
  
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  axiosInterceptor.interceptors.response.use(
    (response) => response, // Pass through successful responses
    (error) => {
      console.log(error)
      if (error.response?.status === 403) {
        console.error('Invalid or expired token detected. Logging out...');
        localStorage.removeItem('token'); // Remove the token
        window.location.href = '/login'; // Redirect to the login page
      }
      return Promise.reject(error);
    }
  );
  export default axiosInterceptor;