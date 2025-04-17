import { Navigate } from 'react-router-dom';

const AuthGuard = ({ children }) => {
  const isAuthenticated = !!localStorage.getItem('token'); // Replace this with your auth logic
  
  if (!isAuthenticated ) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default AuthGuard;
