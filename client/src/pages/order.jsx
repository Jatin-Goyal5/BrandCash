import { Helmet } from "react-helmet-async";
import { OrderView } from "src/sections/order/view";

export default function OrderQrPage (){
     return (
        <>
          <Helmet>
            <title>Place Oder</title>
          </Helmet>
    
          <OrderView />
        </>
      );
    
}