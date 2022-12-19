package io.github.derechtepilz.pilzapi

import io.github.derechtepilz.pilzapi.colorcodes.ColorGradient
import io.github.derechtepilz.pilzapi.colorcodes.ColorHandler

/**
 * The entry point of the MushroomAPI
 */
object PilzAPI {

	/**
	 * Initializes a color gradient. For further interaction with this gradient, you should use the methods of the [ColorGradient] class.
	 *
	 * @param startColor The color the gradient should start with. This should be a hex code
	 * @param endColor The color the gradient should end with. This should be a hex code
	 * @return A [ColorGradient]
	 */
	@JvmStatic
	fun initializeColorGradient(startColor: String, endColor: String): ColorGradient {
		val colorHandler = ColorHandler(startColor, endColor)
		return ColorGradient(colorHandler)
	}

}