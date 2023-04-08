from BuyerShipperServiceInterfaceModule import BuyerInterface, ShipperInterface
from SellerServiceInterfaceModule import SellerBuyerInterface

include "console.iol"

service SellerService{
    execution: concurrent

    inputPort BuyerSeller {
        location: "socket://localhost:8000"
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
            println@Console("I am Seller1, buyer ASK for product "+ product + ", my price is 25.")()
            quote@Buyer(25)
        }
        [accept(order)]{
            println@Console("I am Seller1, ACCEPT order is "+ order)()
            order@Shipper(order)
        }
        [reject(order)]{
            println@Console("I am Seller1, REJECT order is "+ order)()
            println@Console("reject")()
        }

    }
}