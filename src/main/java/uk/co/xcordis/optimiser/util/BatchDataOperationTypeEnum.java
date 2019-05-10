package uk.co.xcordis.optimiser.util;

/**
 * The <code>BatchDataOperationTypeEnum</code> class contains batch data api operation type enum that need in the <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public enum BatchDataOperationTypeEnum {

	ADD("Add"), UPDATE("Update");

	private String operationType;

	BatchDataOperationTypeEnum(final String operationType) {

		this.operationType = operationType;
	}

	public String operationType() {

		return operationType;
	}

	public static BatchDataOperationTypeEnum findByOperationType(final String operationType) {

		for (final BatchDataOperationTypeEnum enumType : values()) {

			if (enumType.operationType.equals(operationType)) {
				return enumType;
			}
		}
		return null;
	}
}
