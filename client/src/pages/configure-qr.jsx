import { Helmet } from "react-helmet-async";
import { ConfigureQrView } from "src/sections/configure-qr/view";

export default function ConfigureQrPage (){
     return (
        <>
          <Helmet>
            <title>Configure Qr</title>
          </Helmet>
    
          <ConfigureQrView />
        </>
      );
    
}