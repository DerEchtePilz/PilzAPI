package io.github.derechtepilz.pilzapi

import io.github.derechtepilz.pilzapi.colorcodes.ColorHandler

object PilzAPI {

	private lateinit var colorHandler: ColorHandler

	@JvmStatic
	fun initializeColorGradient(startColor: String, endColor: String) {
		val colorHandler = ColorHandler(startColor, endColor)
		this.colorHandler = colorHandler
	}

	@JvmStatic
	fun getNextColor(): String {
		colorHandler.nextColor()
		return colorHandler.getGeneratedColor()
	}

}