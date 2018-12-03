package util

/**
 * Utility methods for loading resources.
 */

/**
 * Loads and returns the contents of the specified [resource].
 */
fun loadResource(resource: String): String {
    return object {}.javaClass.getResource(resource).readText()
}