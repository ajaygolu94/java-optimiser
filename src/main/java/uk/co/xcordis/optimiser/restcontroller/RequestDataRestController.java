package uk.co.xcordis.optimiser.restcontroller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
import uk.co.xcordis.optimiser.model.RequestData;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>RequestDataRestController</code> class responsible for handling all the Request Data Rest API request in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class RequestDataRestController extends BaseController implements ApplicationURIConstants, ApplicationConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestDataRestController.class);

	/**
	 * This <code>requestDataList</code> method is used to get the list of Request Data.
	 *
	 * @return
	 */
	@GetMapping(LIST_REQUESTDATA_URL)
	public ResponseEntity<UIOperationResponse> requestDataList() {

		LOGGER.info(" ==> Method : requestDataList ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		final ResponseEntity<UIOperationResponse> responseEntity;

		try {

			final List<RequestData> requestDataList = getServiceRegistry().getRequestDataService().getRequestDataList();

			if (ApplicationUtils.isValid(requestDataList)) {
				ApplicationUtils.sortListByTimeStamp(requestDataList, FIELD_AUDITTIMESTAMP_LABEL, ApplicationConstants.DD_MM_YYYY_HH_MM_SS_AM_PM);
			}

			return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, requestDataList,
					UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> requestDataList ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : requestDataList ==> Exit");
		return responseEntity;
	}

	/**
	 * This <code>viewRequestDataDetails</code> method is used view the Request Data Details.
	 *
	 * @param requestDataId
	 * @return
	 */
	@GetMapping(VIEW_REQUESTDATA_URL)
	public ResponseEntity<UIOperationResponse> viewRequestDataDetails(@PathVariable(required = false) final UUID requestDataId) {

		LOGGER.info(" ==> Method ==> viewRequestDataDetails ==> Enter");

		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			if (requestDataId != null) {

				final RequestData requestData = getServiceRegistry().getRequestDataService().findById(requestDataId);

				if (requestData == null) {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(Arrays
									.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_REQUESTDATA_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {
					return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, requestData,
							UIResponseCodeEnum.VIEW_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, String.valueOf(requestDataId), e, " ==> Method ==> viewRequestDataDetails ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : viewRequestDataDetails ==> Exit");
		return responseEntity;
	}
}
