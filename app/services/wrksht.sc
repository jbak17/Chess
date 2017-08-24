import scala.annotation.tailrec

val arr: Array[Int] = Array(1,2,3,4,5,6,7,8,9,10)


/*
Use a binary search to see if an element exists in a list
 */
@tailrec
def binarySearch(seek: Int, searchSpace: Array[Int]): Boolean = {

  if (seek < searchSpace.head || seek > searchSpace.last){ return false}
  val mid = searchSpace(searchSpace.length/2)
  if (seek == mid) {true}
  else if (seek < mid && mid > seek) binarySearch(seek, searchSpace.take(mid))
  else if (seek > mid && mid < seek ) binarySearch(seek, searchSpace.drop(mid))
  else false
}

println(binarySearch(2, arr))
println(binarySearch(7, arr))
println(binarySearch(11, arr))
