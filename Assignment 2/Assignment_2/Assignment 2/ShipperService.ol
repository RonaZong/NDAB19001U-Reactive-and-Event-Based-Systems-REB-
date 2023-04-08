from ShipperServiceInterfaceModule import ShipperSellerInterface
from BuyerSellerServiceInterfaceModule import BuyerInterface

include "console.iol"

service ShipperService{
    execution: concurrent

    inputPort SellerShipper {
        location: "socket://localhost:8003"
        protocol: http { format = "json" }
        interfaces: ShipperSellerInterface
    }

    outputPort Buyer{
        location: "socket://localhost:8001"
        protocol: http { format = "json" }
        interfaces: BuyerInterface
    }

    main {
        [order(product)]{
            println@Console("I am Shipper, Receive ORDER: "+ product)()
            details@Buyer("invoice for " + product)
        }
    }
}