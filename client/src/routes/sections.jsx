import { lazy, Suspense } from 'react';
import { Outlet, Navigate, useRoutes } from 'react-router-dom';

import DashboardLayout from 'src/layouts/dashboard';
import AuthGuard from './AuthGuard/AuthGuard';

export const IndexPage = lazy(() => import('src/pages/app'));
export const BlogPage = lazy(() => import('src/pages/blog'));
export const UserPage = lazy(() => import('src/pages/user'));
export const LoginPage = lazy(() => import('src/pages/login'));
export const ProductsPage = lazy(() => import('src/pages/products'));
export const AddProductPage = lazy(() => import('src/pages/add-product'));
export const Page404 = lazy(() => import('src/pages/page-not-found'));
export const RegisterPage = lazy(() => import('src/pages/register'));
export const ConfigureQrPage = lazy(() => import('src/pages/configure-qr'));
export const OrderQrPage = lazy(() => import('src/pages/order'));
export const OrderPage = lazy(() => import('src/pages/order-history'));
export const ConfigureAccount = lazy(() => import('src/pages/configure-account'));

// ----------------------------------------------------------------------

export default function Router() {
  const routes = useRoutes([
    {
      element: (
        <AuthGuard>
        <DashboardLayout>
          <Suspense>
            <Outlet />
          </Suspense>
        </DashboardLayout>
        </AuthGuard>
      ),
      children: [
        { element: <IndexPage />, index: true },
        { path: 'user', element: <UserPage /> },
        { path: 'products', element: <ProductsPage /> },
        {path:'configure-qr', element:<ConfigureQrPage/>},
        {path:'order', element:<OrderQrPage/>},
        { path: 'order-history', element: <OrderPage /> },
        {path:'configure-account', element: <ConfigureAccount/>}
      ],
    },
    {
      path: 'login',
      element: <LoginPage />,
    },
    {
      path: 'register',
      element: <RegisterPage />,
    },
    {
      path: '404',
      element: <Page404 />,
    },
    {
      path: '*',
      element: <Navigate to="/404" replace />,
    },
  ]);

  return routes;
}
