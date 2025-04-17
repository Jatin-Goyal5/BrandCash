import React, { useReducer, useState } from 'react';
import userContext from './userContext';
import axios from 'axios';

const UserState = (props) => {
  const [userDetails, setUserDetails] = useState({});
  const [authenticated ,setAuthenticated] = useState(false);
  // const initialState = {
  //   user: null,
  //   isAuthenticated: false,
  // };

  // const authReducer = (state, action) => {
  //   switch (action.type) {
  //     case 'REGISTER_SUCCESS':
  //       return {
  //         ...state,
  //         user: action.payload,
  //         isAuthenticated: false,
  //       };
  //     case 'LOGOUT':
  //       return initialState;
  //     default:
  //       return state;
  //   }
  // };

  // const [state, dispatch] = useReducer(authReducer, initialState);


  const register = async (userData) => {
    try {
      const response = await axios.post('http://localhost:5000/auth/brand/register', userData, {
        headers: { 'Content-Type': 'application/json' },
      });
  
      // Dispatch success action if the request is successful
      dispatch({ type: 'REGISTER_SUCCESS', payload: response.data });
      return response;
    } catch (error) {
      // Handle error responses from the server
      const errorMessage =
        error.response?.data?.message || 'Registration failed';
      console.error(errorMessage);
      throw new Error(errorMessage); // Propagate error to the caller
    }
  };
  
  const login = async (userData) => {
    try {
      const response = await axios.post('http://localhost:5000/auth/brand/login', userData, {
        headers: { 'Content-Type': 'application/json' },
      });
  
      // Dispatch success action if the request is successful
      // dispatch({ type: 'REGISTER_SUCCESS', payload: response.data });
      if(response.status == 200){
        setAuthenticated(true);
        console.log(response?.data.data?.accessToken);
        localStorage.setItem('token',response?.data.data?.accessToken);
        setUserDetails({
          firstName: response?.data.data?.firstName,
          lastName: response?.data.data?.lastName,
          email: response?.data.data?.email,
          token: response?.data.data?.accessToken,
        });
      }
      return response;
    } catch (error) {
      // Handle error responses from the server
      const errorMessage =
        error.response?.data?.message || 'Login failed';
      console.error(errorMessage);
      throw new Error(errorMessage); // Propagate error to the caller
    }
  };
  

  const logout = () => {
    setUserDetails(null);
    setAuthenticated(false);
    localStorage.removeItem('token');
  };

  return <userContext.Provider value={{userDetails, register, logout,login }}>{props.children}</userContext.Provider>;
};

export default UserState;