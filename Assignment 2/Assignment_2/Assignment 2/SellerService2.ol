from BuyerShipperServiceInterfaceModule import BuyerInterface, ShipperInterface
from SellerServiceInterfaceModule import SellerBuyerInterface

include "console.iol"

service SellerService{
    execution: concurrent

    inputPort BuyerSeller {
        location: "socket://localhost:8004"
        protocol: http { format = "json" }
        interfaces: SellerBuyerInterface
    }

    outputPort Buyer {
        location: "socket://localhost:8002"
        protocol: http { format = "json" }
        interfaces: BuyerInterface
    }

    outputPort Shipper {
        location: "socket://localhost:8003"
        protocol: http { format = "json" }
        interfaces: ShipperInterface
    }

    main {
        [ask(product)]{
            println@Console("I am Seller2, buyer ASK for product "+ product + ", my price is 22.")()
            quote2@Buyer(22)
        }
        [accept(order)]{
            println@Console("I am Seller2, ACCEPT order is "+ order)()
            order@Shipper(order)
        }
        [reject(order)]{
            println@Console("I am Seller2, REJECT order is "+ order)()
            println@Console("reject")()
        }

    }
}