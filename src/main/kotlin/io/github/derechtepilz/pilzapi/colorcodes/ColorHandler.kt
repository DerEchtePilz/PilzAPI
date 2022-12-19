package io.github.derechtepilz.pilzapi.colorcodes

import java.util.regex.Pattern

/**
 * This class is used to manage a [ColorGradient]. It is only used internally and should not be used by anyone else!
 */
class ColorHandler(startColor: String, endColor: String) {

	private var firstColor: IntArray
	private var secondColor: IntArray

	private val redRange: Int
	private val greenRange: Int
	private val blueRange: Int

	private var generatedColor: IntArray

	private var isActive = false
	private var vectorToApproach: IntArray
	private val vectorCalculator: VectorCalculator

	init {
		firstColor = HexHandler(startColor).buildArray()
		secondColor = HexHandler(endColor).buildArray()
		generatedColor = firstColor.clone()
		vectorToApproach = secondColor.clone()
		vectorCalculator = VectorCalculator(firstColor, secondColor, vectorToApproach)

		redRange = secondColor[0] - firstColor[0]
		greenRange = secondColor[1] - firstColor[1]
		blueRange = secondColor[2] - firstColor[2]
	}

	fun nextColor() {
		isActive = true
		if (vectorToApproach.contentEquals(secondColor)) {
			if (vectorCalculator.canModifyRed()) {
				generatedColor = vectorCalculator.doStepForRed(generatedColor)
				return
			}
			if (vectorCalculator.canModifyGreen()) {
				generatedColor = vectorCalculator.doStepForGreen(generatedColor)
				return
			}
			if (vectorCalculator.canModifyBlue()) {
				generatedColor = vectorCalculator.doStepForBlue(generatedColor)
				return
			}
			if (!vectorCalculator.canModifyRed() && !vectorCalculator.canModifyGreen() && !vectorCalculator.canModifyBlue()) {
				vectorCalculator.switchArrayToApproach()
			}
			return
		}
		if (vectorToApproach.contentEquals(firstColor)) {
			if (vectorCalculator.canModifyBlue()) {
				generatedColor = vectorCalculator.doStepForBlue(generatedColor)
				return
			}
			if (vectorCalculator.canModifyGreen()) {
				generatedColor = vectorCalculator.doStepForGreen(generatedColor)
				return
			}
			if (vectorCalculator.canModifyRed()) {
				generatedColor = vectorCalculator.doStepForRed(generatedColor)
				return
			}
			if (!vectorCalculator.canModifyRed() && !vectorCalculator.canModifyGreen() && !vectorCalculator.canModifyBlue()) {
				vectorCalculator.switchArrayToApproach()
				return
			}
		}
	}

	fun getGeneratedColor(): String {
		return convertColorToHex(generatedColor[0], generatedColor[1], generatedColor[2])
	}

	private fun convertColorToHex(red: Int, green: Int, blue: Int): String {
		var redHex = Integer.toHexString(red)
		redHex = if (redHex.length == 1) "0$redHex" else redHex

		var greenHex = Integer.toHexString(green)
		greenHex = if (greenHex.length == 1) "0$greenHex" else greenHex

		var blueHex = Integer.toHexString(blue)
		blueHex = if (blueHex.length == 1) "0$blueHex" else blueHex

		return redHex + greenHex + blueHex
	}

	private class VectorCalculator(private var colorOne: IntArray, private var colorTwo: IntArray, var arrayToApproach: IntArray) {

		private var generatedArray = IntArray(3)

		private var canModifyRed = true
		private var canModifyGreen = true
		private var canModifyBlue = true

		fun doStepForRed(currentArray: IntArray): IntArray {
			return if (currentArray[0] == arrayToApproach[0]) {
				generatedArray = currentArray
				canModifyRed = false
				generatedArray
			} else if (currentArray[0] > arrayToApproach[0]) {
				currentArray[0] -= 1
				generatedArray = currentArray
				generatedArray
			} else {
				currentArray[0] += 1
				generatedArray = currentArray
				generatedArray
			}
		}

		fun doStepForGreen(currentArray: IntArray): IntArray {
			return if (currentArray[1] == arrayToApproach[1]) {
				generatedArray = currentArray
				canModifyGreen = false
				generatedArray
			} else if (currentArray[1] > arrayToApproach[1]) {
				currentArray[1] -= 1
				generatedArray = currentArray
				generatedArray
			} else {
				currentArray[1] += 1
				generatedArray = currentArray
				generatedArray
			}
		}

		fun doStepForBlue(currentArray: IntArray): IntArray {
			return if (currentArray[2] == arrayToApproach[2]) {
				generatedArray = currentArray
				canModifyBlue = false
				generatedArray
			} else if (currentArray[2] > arrayToApproach[2]) {
				currentArray[2] -= 1
				generatedArray = currentArray
				generatedArray
			} else {
				currentArray[2] += 1
				generatedArray = currentArray
				generatedArray
			}
		}

		fun switchArrayToApproach() {
			arrayToApproach = if (arrayToApproach.contentEquals(colorOne)) {
				canModifyRed = true
				canModifyGreen = true
				canModifyBlue = true
				colorTwo
			} else {
				canModifyRed = true
				canModifyGreen = true
				canModifyBlue = true
				colorOne
			}
		}

		fun canModifyRed(): Boolean {
			return canModifyRed
		}

		fun canModifyGreen(): Boolean {
			return canModifyGreen
		}

		fun canModifyBlue(): Boolean {
			return canModifyBlue
		}

	}

	private class HexHandler(private val hexString: String) {

		private var isValidColorCode = false

		init {
			if (hexString.length != 6) {
				throw IllegalArgumentException("The given hex string does not match the length for a color code!")
			}
			val regex: Pattern = Pattern.compile("[0-9a-fA-F]+")
			if (!hexString.matches(regex.toRegex())) {
				throw IllegalArgumentException("The given hex string does not match hex syntax!")
			}
			isValidColorCode = true
		}

		fun buildArray(): IntArray {
			if (!isValidColorCode) {
				throw IllegalStateException("Cannot convert an invalid hex string into a vector!")
			}
			val redValue = Integer.parseInt(hexString.substring(0, 2), 16)
			val greenValue = Integer.parseInt(hexString.substring(2, 4), 16)
			val blueValue = Integer.parseInt(hexString.substring(4, 6), 16)

			return intArrayOf(redValue, greenValue, blueValue)
		}

	}

}