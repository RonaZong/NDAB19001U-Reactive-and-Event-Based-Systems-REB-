from SellerShipperServiceInterfaceModule import SellerInterface
from BuyerServiceInterfaceModule import BuyerShipperInterface, BuyerSellerInterface

include "console.iol"

service BuyerService {

//     embed Console as Console
     execution{ single }

    outputPort Seller {
          location: "socket://localhost:8000"
          protocol: http { format = "json" }
          interfaces: SellerInterface
    }

    outputPort Seller2 {
          location: "socket://localhost:8004"
          protocol: http { format = "json" }
          interfaces: SellerInterface
    }

     inputPort ShipperBuyer {
          location: "socket://localhost:8001"
          protocol: http { format = "json" }
          interfaces: BuyerShipperInterface
     }

     inputPort SellerBuyer {
          location: "socket://localhost:8002"
          protocol: http { format = "json" }
          interfaces: BuyerSellerInterface
     }


    main {
          { 
            ask@Seller("chips"); quote(price1) 
            | ask@Seller2("chips") ;quote2(price2)
          };
          println@Console("Seller 1's price is: " + price1)()
          println@Console("Seller 2's price is: " + price2)()
     }
}
