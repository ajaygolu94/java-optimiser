package uk.co.xcordis.optimiser.util;

public class DataEnum {

	public enum SelectorKey {
		GATEWAY_SUPPORTED_CAPACITY_("Gateway Supported Capacity/Volume"), CARD_TYPE("Card Type"), PREFERED_COUNTRY("Peferred Country"),
		SUCCESS_RATE("Success Rate"), PREFERNCE_NUMBER("Preference Number"), SEQUENCE_NUMBER("Sequence Number"), OTHER("Other");

		private final String keyType;

		SelectorKey(final String keyType) {
			this.keyType = keyType;
		}

		/**
		 * @return the keyType
		 */
		public String getKeyType() {

			return keyType;
		}
	}
}
