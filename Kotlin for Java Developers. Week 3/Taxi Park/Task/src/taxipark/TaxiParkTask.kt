package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    this.allDrivers.minus(trips.map { it.driver })
//    this.allDrivers.filter { driver -> trips.none { it.driver == driver } }.toSet()
//    this.allDrivers.filter { driver -> driver !in this.trips.map { trip -> trip.driver } }.toSet()



/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
//    return trips
//        .flatMap(Trip::passengers)
//        .groupBy { passenger -> passenger }
//        .filterValues { listP -> listP.size >= minTrips }
//        .keys

//    return allPassengers.filter {
//        p -> trips.count { p in it.passengers } >= minTrips
//    }.toSet()

    val map = ::setOfPassengers
    return map(this.trips, this.allPassengers).filterValues { i -> i >= minTrips }.keys.toSet()
}

fun setOfPassengers(trips : List<Trip>, passengers : Set<Passenger>): HashMap<Passenger, Int> {
    val map = hashMapOf<Passenger, Int>()
    passengers.forEach { passenger ->
        map[passenger] = 0
        trips.forEach { if (passenger in it.passengers){
            map[passenger] = map.getOrDefault(passenger, 0) + 1
        } } }
    return map
}

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    val dr = this.trips.filter { it.driver == driver }
    val map = setOfPassengers(dr, this.allPassengers)
    return map.filterValues { i -> i >= 2 }.keys
}
/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val (first, second) = this.trips.partition { it.discount != null && it.discount > 0.0 }
//    return allPassengers.filter {
//        p -> first.count { p in it.passengers } > second.count { p in it.passengers }
//    }.toSet()

    val map1 = setOfPassengers(first, this.allPassengers)
    val map2 = setOfPassengers(second, this.allPassengers)
    return this.allPassengers.filter { map1[it]!! > map2[it]!! }.toSet()
}

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
//    val a = trips.groupBy {
//        val str = it.duration/10 * 10
//        val end = str + 9
//        str..end
//    }.maxBy { (_, group) -> group.size }
//        .key
//    return a

    var ans : IntRange? = null
    val max = this.trips.maxByOrNull { it.duration }
    val min = this.trips.minByOrNull { it.duration }
    if (max != null && min != null) {
        val map = hashMapOf<IntRange, Int>()
        val r = ArrayList<IntRange>()
        val start = (min.duration / 10) * 10
        val end = (max.duration / 10) * 10 + 9

        for (i in start..end step 10){
            val rstart = i
            val rend = i+9
            r.add(rstart..rend)
        }

        r.forEach { range ->
            map[range] = 0
            trips.forEach { if (it.duration in range){
                map[range] = map.getOrDefault(range, 0) + 1
            } } }
        ans = map.filter { (_,v) -> v == map.values.max() }.keys.first()
    }

    return ans
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    if (this.trips.isEmpty()) return false

//    val totalIncome = trips.sumOf(Trip::cost)
//    val sortedDriversIncome: List<Double> = trips
//        .groupBy(Trip::driver)
//        .map { (_, tripsByDriver) -> tripsByDriver.sumOf(Trip::cost) }
//        .sortedDescending()
//
//    val numberOfTopDrivers = (0.2 * allDrivers.size).toInt()
//    val incomeByTopDrivers = sortedDriversIncome
//        .take(numberOfTopDrivers)
//        .sum()
//
//    return incomeByTopDrivers >= 0.8 * totalIncome

    val map = hashMapOf<Driver, Double>()
    this.trips.forEach {
        map[it.driver] = map.getOrDefault(it.driver, 0.0) + it.cost
    }
    val percentTotalCost = map.values.sum() * 0.8
    val sortedMap = map.toSortedMap(comparator = compareByDescending { map[it] })
    val percentDrivers = (this.allDrivers.size * 2)/10
    if(map.size <= percentDrivers) return true else{
        val driverIncome = sortedMap.entries.take(percentDrivers).sumOf { it.value }
        if (driverIncome >= percentTotalCost) return true
    }
    return false
}