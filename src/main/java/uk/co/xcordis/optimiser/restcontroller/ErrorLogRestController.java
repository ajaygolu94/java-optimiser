package uk.co.xcordis.optimiser.restcontroller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.ErrorLog;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>ErrorLogRestController</code> class is responsible for exposing the API for Error Log module in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class ErrorLogRestController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorLogRestController.class);

	/**
	 * This <code>getErrorLogsList</code> method is used to get the list of all the Error Logs.
	 *
	 * @return
	 */
	@GetMapping(LIST_ERROR_LOG_URL)
	public ResponseEntity<UIOperationResponse> getErrorLogsList() {

		LOGGER.info(" ==> Method : getErrorLogsList ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			return new ResponseEntity<>(
					getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null,
							getServiceRegistry().getErrorLogService().getErrorLogList(), UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode()),
					HttpStatus.OK);

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> getErrorLogsList ==> Exception ==> ");

			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : getErrorLogsList ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>viewErrorLogDetails</code> method is used to get the details of the Error logs.
	 *
	 * @param errorLogID
	 * @return
	 */
	@GetMapping(VIEW_ERROR_LOG_URL)
	public ResponseEntity<UIOperationResponse>
	viewErrorLogDetails(@PathVariable(value = ApplicationConstants.ERROR_LOG_ID_KEY, required = false) final String errorLogId) {

		LOGGER.info(" ==> Method ==> viewErrorLogDetails ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (!ApplicationUtils.isEmpty(errorLogId)) {

				final ErrorLog errorlogDetails = getServiceRegistry().getErrorLogService().findById(UUID.fromString(errorLogId));

				if (errorlogDetails == null) {

					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays.asList(
									getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_LOG_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {

					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, errorlogDetails,
							UIResponseCodeEnum.VIEW_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
				}
			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, errorLogId, e, " ==> Method ==> viewErrorLogDetails ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : viewErrorLogDetails ==> Exit");
		return responseEntity;
	}
}