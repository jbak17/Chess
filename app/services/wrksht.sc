
case class Location(R: Int,C: Int){

  val ColumnMap: Map[Int, Char] = Map(1->'A', 2->'B', 3->'C', 4->'D', 5->'E', 6->'F', 7->'G', 8->'H')

  override def toString: String = ColumnMap(C) + R.toString

}

val location = Location(2, 6)

print(location)

val board: List[Location] = (for (c <- 1 to 8; r <- 1 to 8) yield Location(c,r)).toList

val columns = Set()