package day03

/**
 * A claim.
 */
data class Claim(
    val id: Int,
    val left: Int,
    val top: Int,
    val width: Int,
    val height: Int
) {
    /**
     * Returns the coordinates of each square inch in this claim.
     */
    fun getAllCoordinates(): List<Coordinate> {
        return List(width * height) {
            Coordinate(left + it % width, top + it / width)
        }
    }

    companion object {
        const val CLAIM_PATTERN = "#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)"
        val CLAIM_REGEX = Regex(CLAIM_PATTERN)

        /**
         * Parses a string representing a claim.
         */
        fun parse(claim: String): Claim {
            val values = checkNotNull(CLAIM_REGEX.matchEntire(claim)?.groupValues)
            return Claim(
                values[1].toInt(),
                values[2].toInt(),
                values[3].toInt(),
                values[4].toInt(),
                values[5].toInt())
        }
    }
}