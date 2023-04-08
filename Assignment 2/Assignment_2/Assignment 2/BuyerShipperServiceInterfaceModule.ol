interface BuyerInterface {
    OneWay:
	    quote (int),
        quote2 (int)
}

interface ShipperInterface {
    OneWay:
        order( string )
}