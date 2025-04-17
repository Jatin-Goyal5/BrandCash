import { Helmet } from 'react-helmet-async';
import OrderHistoryPage from 'src/sections/order-history/view/order-history-view';
import { OrderView } from 'src/sections/order/view';

// ----------------------------------------------------------------------

export default function OrderPage() {
  return (
    <>
      <Helmet>
        <title> Order history</title>
      </Helmet>

      <OrderHistoryPage />
    </>
  );
}
