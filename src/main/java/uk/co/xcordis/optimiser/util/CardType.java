package uk.co.xcordis.optimiser.util;

import java.util.regex.Pattern;

/**
 * The <code>CardType</code> enum contains all card type partter support by application. Also, responsible for detect card type base on card number in
 * <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public enum CardType {

	UNKNOWN, VISA("^4[0-9]{6,}$"), MASTER("^5[1-5][0-9]{5,}|222[1-9][0-9]{3,}|22[3-9][0-9]{4,}|2[3-6][0-9]{5,}|27[01][0-9]{4,}|2720[0-9]{3,}$"),
	AMERICANEXPRESS("^3[47][0-9]{5,}$"), DINERSCLUB("^3(?:0[0-5]|[68][0-9])[0-9]{4,}$"), DISCOVER("^6(?:011|5[0-9]{2})[0-9]{3,}$"),
	JCB("^(?:2131|1800|35[0-9]{3})[0-9]{3,}$"), CHINAUNIONPAY("^62[0-9]{14,17}$"), VISAELECTRON("^(4026|417500|4405|4508|4844|4913|4917)\\d+$"),
	INTERPAYMENT("^(636)\\d+$"), MAESTRO("^(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\\d+$"), DANKORT("^(5019)\\d+$"),;

	private Pattern pattern;

	CardType() {

		pattern = null;
	}

	CardType(final String pattern) {

		this.pattern = Pattern.compile(pattern);
	}

	/**
	 * This <code>detect</code> method is use to detect card type base on card number.
	 *
	 * @param cardNumber
	 * @return
	 */
	public static CardType detect(final String cardNumber) {

		for (final CardType cardType : CardType.values()) {
			if (null == cardType.pattern) {
				continue;
			}
			if (cardType.pattern.matcher(cardNumber).matches()) {
				return cardType;
			}
		}

		return UNKNOWN;
	}

}