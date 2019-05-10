package uk.co.xcordis.optimiser.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.ApplicationConfiguration;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>ApplicationConfigurationRestController</code> class responsible for handling all the request related to Application Configuration Module in
 * <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class ApplicationConfigurationRestController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfigurationRestController.class);

	/**
	 * This <code>listAppConfigs</code> method is used to get the list of all Application Configurations from DB.
	 *
	 * @return
	 */
	@GetMapping(LIST_APP_CONFIG_URL)
	public ResponseEntity<UIOperationResponse> listAppConfigs() {

		LOGGER.info(" ==> Method : listAppConfigs ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null,
					getServiceRegistry().getAppConfigService().getAppConfigList(), UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

		} catch (Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> listAppConfigs ==> Exception ==> ");

			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : listAppConfigs ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>deleteAppConfig</code> method is used to delete the Application Configuration Details.
	 *
	 * @param appConfigId
	 * @return
	 */
	@DeleteMapping(DELETE_APP_CONFIG_URL)
	public ResponseEntity<UIOperationResponse>
			deleteAppConfig(@PathVariable(value = ApplicationConstants.APP_CONFIG_ID_LABEL, required = false) final UUID appConfigId) {

		LOGGER.info(" ==> Method ==> deleteAppConfig ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (appConfigId == null) {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			} else {

				ApplicationConfiguration applicationConfiguration = getServiceRegistry().getAppConfigService().getAppConfigById(appConfigId);

				if (applicationConfiguration != null) {
					getServiceRegistry().getAppConfigService().deleteAppConfig(applicationConfiguration);
					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
							new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode(), APP_CONFIG_LABEL))), null,
							UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
				} else {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays
									.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_APP_CONFIG_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				}
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(appConfigId), e, " ==> Method ==> deleteAppConfig ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method ==> deleteAppConfig ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>addAppConfig</code> method is used to add the add Application Configuration details.
	 *
	 * @param applicationConfiguration
	 * @return
	 */
	@PostMapping(ADD_APP_CONFIG_URL)
	public ResponseEntity<UIOperationResponse> addAppConfig(@RequestBody(required = false) final ApplicationConfiguration applicationConfiguration) {

		LOGGER.info(" ==> Method ==> addAppConfig ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (applicationConfiguration != null) {

				errors = validateAppConfigDetails(applicationConfiguration, Boolean.TRUE);

				if (!ApplicationUtils.isValid(errors)) {

					// Add Application Configuration in appconfig Table
					getServiceRegistry().getAppConfigService().addAppConfig(applicationConfiguration);
					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
							new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode(), APP_CONFIG_LABEL))),
							applicationConfiguration.getAppConfigId(), UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

				} else {
					responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
				}

			} else {

				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(applicationConfiguration.getAppConfigId()), e, " ==> Method ==> addAppConfig ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : addAppConfig ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>validateAppConfigDetails</code> method is used to validate the Application Configuration details.
	 *
	 * @param applicationConfiguration
	 * @return
	 */
	private List<String> validateAppConfigDetails(final ApplicationConfiguration applicationConfiguration, final Boolean isAdd) {

		LOGGER.info(" ==> Method ==> validateAppConfigDetails ==> Enter");

		final List<String> errors = new ArrayList<>();

		if (ApplicationUtils.isEmpty(applicationConfiguration.getCode())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.APP_CONFIG_CODE_ERROR.message(), null, null));
		} else {

			if (!ApplicationUtils.isOnlyAlphaNumericDashUnderscoreStartWithAlpha(applicationConfiguration.getCode())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHANUMERICDASHUNDERSCORE.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.APP_CONFIG_CODE_ERROR.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.START_WITH_ALPHA.message(), null, null));
			}

			if (isAdd && ApplicationUtils.isValid(getServiceRegistry().getAppConfigService().getAppConfigList().stream().map(ApplicationConfiguration::getCode)
					.collect(Collectors.toList()).stream().filter(a -> a.equalsIgnoreCase(applicationConfiguration.getCode())).collect(Collectors.toList()))) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
						new Object[] { getMessageSource().getMessage(ErrorDataEnum.APP_CONFIG_CODE_ERROR.message(), null, null) }, null));
			} else {
				if (!isAdd
						&& !getServiceRegistry().getAppConfigService().getAppConfigById(applicationConfiguration.getAppConfigId()).getCode()
								.equalsIgnoreCase(applicationConfiguration.getCode())
						&& ApplicationUtils.isValid(getServiceRegistry().getAppConfigService().getAppConfigList().stream()
								.map(ApplicationConfiguration::getCode).collect(Collectors.toList()).stream()
								.filter(a -> a.equalsIgnoreCase(applicationConfiguration.getCode())).collect(Collectors.toList()))) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.APP_CONFIG_CODE_ERROR.message(), null, null) }, null));
				}
			}
		}

		if (ApplicationUtils.isEmpty(applicationConfiguration.getValue())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_BLANK.message(), null, null)
					+ getMessageSource().getMessage(ErrorDataEnum.APP_CONFIG_VALUE_ERROR.message(), null, null));
		}

		LOGGER.info(" ==> Method ==> validateAppConfigDetails ==> Exit");
		return errors;
	}

	/**
	 * This <code>editAppConfig</code> method is used to edit/update a Application Configuration Details.
	 *
	 * @param applicationConfiguration
	 * @return
	 */
	@PostMapping(EDIT_APP_CONFIG_URL)
	public ResponseEntity<UIOperationResponse> editAppConfig(@RequestBody(required = false) final ApplicationConfiguration applicationConfiguration) {

		LOGGER.info(" ==> Method ==> editAppConfig ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		List<String> errors = new ArrayList<>();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (applicationConfiguration != null && applicationConfiguration.getAppConfigId() != null) {

				ApplicationConfiguration applicationConfigurationDetails = getServiceRegistry().getAppConfigService()
						.getAppConfigById(applicationConfiguration.getAppConfigId());

				if (applicationConfigurationDetails == null && applicationConfiguration.getAppConfigId() == null) {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays
									.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_APP_CONFIG_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {

					errors = validateAppConfigDetails(applicationConfiguration, Boolean.FALSE);

					if (!ApplicationUtils.isValid(errors)) {

						// Update Rule Selector Key Data in Rule Table
						getServiceRegistry().getAppConfigService().updateAppConfig(applicationConfiguration);
						return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(),
								new HashSet<>(Arrays.asList(getMessageByCode(UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode(), APP_CONFIG_LABEL))),
								applicationConfiguration.getAppConfigId(), UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

					} else {
						responseEntity = commonErrorResponse(uiOperationResponse, new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
					}
				}

			} else {

				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(applicationConfiguration.getAppConfigId()), e, " ==> Method ==> editAppConfig ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : editAppConfig ==> Exit");
		return responseEntity;
	}
}
