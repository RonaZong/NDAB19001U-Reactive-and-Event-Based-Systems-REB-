// 要收到的消息
interface BuyerShipperInterface {
    OneWay:
        details( string)
}

interface BuyerSellerInterface {
    OneWay:
        quote( int ),
        quote2(int)
}


