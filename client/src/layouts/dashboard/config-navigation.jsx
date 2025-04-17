import SvgColor from 'src/components/svg-color';

// ----------------------------------------------------------------------

const icon = (name) => (
  <SvgColor src={`/assets/icons/navbar/${name}.svg`} sx={{ width: 1, height: 1 }} />
);

const navConfig = [
  {
    title: 'dashboard',
    path: '/',
    icon: icon('ic_pie'),
  },
  {
    title: 'user',
    path: '/user',
    icon: icon('ic_user'),
  },
  {
    title: 'product',
    path: '/products',
    icon: icon('ic_cart'),
  },
  // {
  //   title:'Add Product',
  //   path:'/add-product',
  //   icon:icon('ic_cart')
  // },
  {
    title:'Configure QR',
    path:'/configure-qr',
    icon:icon('ic_qr')
  },
  {
    title: 'order history',
    path: '/order-history',
    icon: icon('ic_history'),
  },
  {
    title: 'Configure Account',
    path: '/configure-account',
    // icon: icon('ic_settings'),
  }
  // {
  //   title: 'blog',
  //   path: '/blog',
  //   icon: icon('ic_blog'),
  // },
  // {
  //   title: 'login',
  //   path: '/login',
  //   icon: icon('ic_lock'),
  // },
  // {
  //   title: 'Not found',
  //   path: '/404',
  //   icon: icon('ic_disabled'),
  // },
];

export default navConfig;
