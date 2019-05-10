package uk.co.xcordis.optimiser.util;

import java.util.Arrays;
import java.util.List;

/**
 * The <code>MerchantRuleParamKeyEnum</code> class contains list of constants of merchant rule parameter key in the <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
public enum MerchantRuleParamKeyEnum {

	GATEWAYVOLUMERULE("coreVolumeURL", "coreAccesstoken"),;

	private List<String> key;

	MerchantRuleParamKeyEnum(final String... keys) {

		key = Arrays.asList(keys);
	}

	public List<String> keys() {

		return key;
	}
}
