package taxipark
/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    this.allDrivers.subtract( this.trips.map { it.driver }.toSet() )


/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
     this.allPassengers.filter{p -> this.trips.count{p in it.passengers}>=minTrips }.toSet()



/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    this.allPassengers.filter{p -> this.trips.count{p in it.passengers && it.driver==driver}>1 }.toSet()


/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    this.allPassengers.filter{p -> this.trips.count{p in it.passengers}/2 < this.trips.count{p in it.passengers && it.discount!=null} }.toSet()


/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {

    if(this.trips.isEmpty())
    return null

    val updateDuration= this.trips.map{ (it.duration / 10)*10 } // remove units digit from all durations
    val countDuration=updateDuration.map{ u-> updateDuration.count{ u==it } }.toList() // count all recurrence of each duration
    return updateDuration[countDuration.indexOf(countDuration.max())]..updateDuration[countDuration.indexOf(countDuration.max())]+9
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if(this.trips.isEmpty())
        return false


    val driversTrips =  mutableMapOf<String, Double>() // mutable to be modified

    for (trip in this.trips)
            driversTrips.merge(trip.driver.name,trip.cost) { cost, sumCost -> cost + sumCost }

    val sortedTrips = driversTrips.toList().sortedByDescending{ it.second }


    val numberOf20Percent=(this.allDrivers.size*20)/100
    var costSumOf20Percent=0.0

    for (i in 0 until  numberOf20Percent)
        costSumOf20Percent+=sortedTrips[i].second

    val costSum= this.trips.sumOf { it.cost }

    return (costSumOf20Percent/costSum)>=0.8

}