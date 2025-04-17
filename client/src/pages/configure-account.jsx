import { Helmet } from 'react-helmet-async';
import { ConfigureAccountView } from 'src/sections/configure-account/view';
// import OrderHistoryPage from 'src/sections/order-history/view/order-history-view';
// import { OrderView } from 'src/sections/order/view';

// ----------------------------------------------------------------------

export default function ConfigureAccount() {
  return (
    <>
      <Helmet>
        <title> Configure Account</title>
      </Helmet>

<ConfigureAccountView></ConfigureAccountView>
    </>
  );
}
