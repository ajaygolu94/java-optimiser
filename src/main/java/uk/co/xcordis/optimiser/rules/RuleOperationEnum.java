package uk.co.xcordis.optimiser.rules;

/**
 * The <code>RuleOperationEnum</code> class contains all Constants related to rules operation(Like equals, grater then and more) in <b>Optimiser</b>
 * application.
 *
 * @author Rob Atkin
 */
public enum RuleOperationEnum {

	EQUALSTO("Equal", "equalsTo"), GREATERTHAN("Greater Than", "graterThan"), LESSTHAN("Less Than", "lessThan"), RANGE("Range", "range");

	private String name;
	private String methodName;

	private RuleOperationEnum(final String name, final String methodName) {

		this.name = name;
		this.methodName = methodName;
	}

	/**
	 * @return the name
	 */
	public String getName() {

		return name;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {

		return methodName;
	}

}
