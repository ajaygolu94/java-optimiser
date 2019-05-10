package uk.co.xcordis.optimiser.rules;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;

/**
 * The <code>RulesOperationValidator</code> class contains all methods of rules operation validation in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public class RulesOperationValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(RulesOperationValidator.class);

	/**
	 * This <code>equalsTo</code> method is use to perform equals operation between selectorValue and objectValue
	 *
	 * @param selectorValue
	 * @param objectValue
	 * @return
	 */
	public Boolean equalsTo(final String selectorValue, final String objectValue) {

		LOGGER.info(" ==> Method ==> equalsTo ==> Called");

		try {

			if (ApplicationUtils.isDoubleNumber(objectValue) && ApplicationUtils.isDoubleNumber(selectorValue)) {
				return Double.valueOf(selectorValue).compareTo(Double.valueOf(objectValue)) == 0 ? Boolean.TRUE : Boolean.FALSE;
			} else {
				return selectorValue.equalsIgnoreCase(objectValue);
			}

		} catch (final Exception e) {
			LOGGER.error(ApplicationConstants.MARKER, " ==> Method ==> equalsTo ==> Exception ==> " + e);
		}
		return Boolean.FALSE;
	}

	/**
	 * This <code>graterThen</code> method is use to perform grater then operation between selectorValue and objectValue
	 *
	 * @param selectorValue
	 * @param objectValue
	 * @return
	 */
	public Boolean graterThen(final String selectorValue, final String objectValue) {

		LOGGER.info(" ==> Method ==> graterThen ==> Called");

		try {

			return Double.valueOf(objectValue) > Double.valueOf(selectorValue) ? Boolean.TRUE : Boolean.FALSE;
		} catch (final Exception e) {
			LOGGER.error(ApplicationConstants.MARKER, " ==> Method ==> graterThen ==> Exception ==> " + e);
		}
		return Boolean.FALSE;
	}

	/**
	 * This <code>lessThen</code> method is use to perform less then operation between selectorValue and objectValue
	 *
	 * @param selectorValue
	 * @param objectValue
	 * @return
	 */
	public Boolean lessThen(final String selectorValue, final String objectValue) {

		LOGGER.info(" ==> Method ==> lessThen ==> Called");

		try {

			return Double.valueOf(objectValue) < Double.valueOf(selectorValue) ? Boolean.TRUE : Boolean.FALSE;
		} catch (final Exception e) {
			LOGGER.error(ApplicationConstants.MARKER, " ==> Method ==> lessThen ==> Exception ==> " + e);
		}
		return Boolean.FALSE;
	}

	/**
	 * This <code>range</code> method is use to perform range operation between selectorValue and objectValue
	 *
	 * @param selectorValue
	 * @param objectValue
	 * @return
	 */
	public Boolean range(final String selectorValue, final String objectValue) {

		LOGGER.info(" ==> Method ==> range ==> Called");

		try {

			if (selectorValue.contains("-") && !ApplicationUtils.isEmpty(selectorValue.split("-"))) {

				final String[] selectorValues = selectorValue.split("-");

				if (!ApplicationUtils.isEmpty(selectorValues[0]) && !ApplicationUtils.isEmpty(selectorValues[1])) {

					return Double.valueOf(objectValue) > Double.valueOf(selectorValues[0]) && Double.valueOf(objectValue) < Double.valueOf(selectorValues[1])
							? Boolean.TRUE
							: Boolean.FALSE;
				}
			}
		} catch (final Exception e) {
			LOGGER.error(ApplicationConstants.MARKER, " ==> Method ==> range ==> Exception ==> " + e);
		}
		return Boolean.FALSE;
	}
}
