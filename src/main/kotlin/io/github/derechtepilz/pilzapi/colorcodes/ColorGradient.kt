package io.github.derechtepilz.pilzapi.colorcodes

/**
 * This class is used to interact with a [ColorHandler].
 */
class ColorGradient(private val colorHandler: ColorHandler) {

	/**
	 * This method returns the next color of this color gradient.
	 *
	 * @return The next color in the color gradient. This is a hex string.
	 */
	fun getNextColor(): String {
		colorHandler.nextColor()
		return colorHandler.getGeneratedColor()
	}

}