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
        
        if(price1 < 20 && price2 < 20){
            if(price1 < price2){
                println@Console("Seller 1 is more cheaper... Buy from Seller 1, Reject Seller 2")()
                {
                    accept@Seller("Ok to buy chips for " + price1); details(invoice); println@Console("Receive "+invoice+ " from Shipper!")()
                    | reject@Seller2("Not ok to buy chips for "+ price2)
                }
            } 
            else{
                println@Console("Seller 2 is more cheaper... Buy from Seller 2, Reject Seller 1")()
                {
                    accept@Seller2("Ok to buy chips for " + price2); details(invoice); println@Console("Receive "+invoice+ " from Shipper!")()
                    | reject@Seller("Not ok to buy chips for "+ price1)
                }
            }
        }
        else if (price1<20){
            println@Console("Seller 1 is more cheaper... Buy from Seller 1, Reject Seller 2")()
            {
                accept@Seller("Ok to buy chips for " + price1); details(invoice); println@Console("Receive "+invoice+ " from Shipper!")()
                | reject@Seller2("Not ok to buy chips for "+ price2)
            }
        }
        else if (price2<20){
            println@Console("Seller 2 is more cheaper... Buy from Seller 2, Reject Seller 1")()
            {
                accept@Seller2("Ok to buy chips for " + price2); details(invoice); println@Console("Receive "+invoice+ " from Shipper!")()
                | reject@Seller("Not ok to buy chips for "+ price1)
            }
        }
        else{
            println@Console("Reject Both...")()
            {
                reject@Seller("Not ok to buy chips for "+ price1)
                | reject@Seller2("Not ok to buy chips for "+ price2)
            }
            
        }

    }
}
