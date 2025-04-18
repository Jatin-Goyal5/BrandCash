/* eslint-disable perfectionist/sort-imports */
import 'src/global.css';

import { useScrollToTop } from 'src/hooks/use-scroll-to-top';

import Router from 'src/routes/sections';
import ThemeProvider from 'src/theme';
import UserState from './context/user/UserState';
import AuthGuard from './routes/AuthGuard/AuthGuard';
import ToastState from './context/user/ToastState';
// ----------------------------------------------------------------------

export default function App() {
  useScrollToTop();

  return (
    <ThemeProvider>
   <ToastState>
    <UserState>
          <Router />
      </UserState>
   </ToastState>
    </ThemeProvider>
  );
}
