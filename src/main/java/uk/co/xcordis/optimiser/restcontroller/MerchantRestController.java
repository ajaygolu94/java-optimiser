/*
 * Copyright (c) XCordis FinTech Ltd 2010-2017.
 *
 * This software is copyrighted. Under the copyright laws, this software may not be copied, in whole or in part, without prior written consent of XCordis
 * FinTech Ltd. This software is provided under the terms of a license between XCordis FinTech Ltd and the recipient, and its use is subject to the terms of
 * that license.
 */
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
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import uk.co.xcordis.optimiser.controller.BaseController;
import uk.co.xcordis.optimiser.dto.MerchantDto;
import uk.co.xcordis.optimiser.dto.UIOperationResponse;
import uk.co.xcordis.optimiser.model.ApplicationUser;
import uk.co.xcordis.optimiser.model.Merchant;
import uk.co.xcordis.optimiser.util.ApplicationConstants;
import uk.co.xcordis.optimiser.util.ApplicationURIConstants;
import uk.co.xcordis.optimiser.util.ApplicationUtils;
import uk.co.xcordis.optimiser.util.ErrorDataEnum;
import uk.co.xcordis.optimiser.util.RequestResponseStatusEnum;
import uk.co.xcordis.optimiser.util.UIResponseCodeEnum;

/**
 * The <code>MerchantRestController</code> class responsible for expose API for merchant in <b>Optimiser</b> application.
 *
 * @author Rob Atkin
 */
@RestController
public class MerchantRestController extends BaseController implements ApplicationConstants, ApplicationURIConstants {

	private static final Logger LOGGER = LoggerFactory.getLogger(MerchantRestController.class);

	/**
	 * The method <code>merchantList</code> is responsible for merchant page screen in Optimiser application.
	 *
	 * @param userId
	 * @return
	 */
	@GetMapping(value = MERCHANT_LIST_URL)
	public ResponseEntity<UIOperationResponse> merchantList(@RequestHeader(value = USERID_LABLE, required = false) final String userId) {

		LOGGER.info(" ==> Method : merchantList ==> Enter");

		List<MerchantDto> merchantList = new ArrayList<>();
		final UIOperationResponse uiOperationResponse = new UIOperationResponse();
		ResponseEntity<UIOperationResponse> responseEntity;

		try {

			final ApplicationUser applicationUser = getServiceRegistry().getUserService().getUserByOpenId(userId);

			if (applicationUser != null && !ApplicationUtils.isEmpty(applicationUser.getRole())) {

				if (ApplicationConstants.ROLE_MERCHANT.equalsIgnoreCase(applicationUser.getRole())) {

					merchantList = getMerchantListByUserId(String.valueOf(applicationUser.getUserId()));

				} else if (ApplicationConstants.ROLE_MERCHANT_MANAGER.equalsIgnoreCase(applicationUser.getRole())) {

					final ApplicationUser user = getServiceRegistry().getUserService().getUserByManagerId(applicationUser.getUserId());

					if (user != null) {
						merchantList = getMerchantListByUserId(String.valueOf(user.getUserId()));
					}
				}

				return new ResponseEntity<>(getUiOperationResponse(RequestResponseStatusEnum.SUCCESS.status(), null, merchantList,
						UIResponseCodeEnum.LIST_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(
								Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_COMMON_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.NOT_FOUND);
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> merchantList ==> Exception ==> ");
			responseEntity = commonErrorResponse(uiOperationResponse,
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info(" ==> Method : merchantList ==> Exit");
		return responseEntity;
	}

	/**
	 * The method <code>viewMerchant</code> is responsible for load merchant data by merchant id.
	 *
	 * @param merchantId
	 * @return
	 */
	@GetMapping(value = MERCHANT_VIEW_URL)
	public ResponseEntity<UIOperationResponse>
			viewMerchant(@PathVariable(value = ApplicationConstants.MERCHANTID_LABLE, required = false) final String merchantId) {

		LOGGER.info(" ==> Method : viewMerchant ==> Called");

		Merchant merchant = null;
		ResponseEntity<UIOperationResponse> responseEntity = null;
		try {
			if (!ApplicationUtils.isEmpty(merchantId)) {
				merchant = getServiceRegistry().getMerchantService().getMerchantByMerchantId(UUID.fromString(merchantId));
				if (merchant != null) {
					responseEntity = new ResponseEntity<>(
							getUiOperationResponse(ApplicationConstants.STATUS_SUCCESS, null, merchant, UIResponseCodeEnum.VIEW_SUCCESS_MESSAGE.getCode()),
							HttpStatus.OK);
				} else {
					responseEntity = commonErrorResponse(getUiOperationResponse(null, null, null, null),
							new HashSet<>(
									Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_COMMON_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				}
			} else {
				responseEntity = commonErrorResponse(new UIOperationResponse(),
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}
		} catch (final Exception e) {

			logError(LOGGER, merchantId, e, " ==> Method ==> viewMerchant ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	/**
	 * The method <code>editMerchant</code> is responsible for update the merchant data in the database.
	 *
	 * @param merchant
	 * @return
	 */
	@PostMapping(value = MERCHANT_EDIT_URL)
	public ResponseEntity<UIOperationResponse> editMerchant(@RequestBody(required = false) final Merchant merchant) {

		LOGGER.info(" ==> Method : editMerchant ==> called ");

		ResponseEntity<UIOperationResponse> responseEntity = null;
		final UIOperationResponse uiOperationResponse = new UIOperationResponse();

		try {

			if (merchant != null && merchant.getMerchantId() != null) {

				final HashSet<String> errors = validateMerchant(merchant, Boolean.FALSE);

				final Merchant merchantDetails = getServiceRegistry().getMerchantService().getMerchantByMerchantId(merchant.getMerchantId());

				if (merchantDetails == null) {
					responseEntity = commonErrorResponse(uiOperationResponse,
							new HashSet<>(
									Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_COMMON_NOT_FOUND_ERROR_MESSAGE.message(), null, null))),
							HttpStatus.NOT_FOUND);
				} else {

					if (!ApplicationUtils.isValid(errors)) {

						final Merchant merchantData = getServiceRegistry().getMerchantService().saveMerchant(merchant);

						responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_SUCCESS,
								new HashSet<>(Arrays.asList(ApplicationConstants.MERCHANT_MODULE_LABEL
										+ getMessageSource().getMessage(ErrorDataEnum.EDIT_SUCCESS_MESSAGE.message(), null, null))),
								merchantData, UIResponseCodeEnum.EDIT_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);
					} else {

						responseEntity = commonErrorResponse(new UIOperationResponse(), new LinkedHashSet<String>(errors), HttpStatus.BAD_REQUEST);
					}
				}

			} else {
				responseEntity = commonErrorResponse(uiOperationResponse,
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}

		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> editMerchant ==> Exception ==> ");
			commonErrorResponse(new UIOperationResponse(),
					new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_ERROR_MESSAGE.message(), null, null))),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	/**
	 * The method <code>merchantDelete</code> is responsible for save the merchant data in the database.
	 *
	 * @param merchantId
	 * @return
	 */
	@DeleteMapping(value = MERCHANT_DELETE_URL)
	public ResponseEntity<UIOperationResponse>
			merchantDelete(@PathVariable(value = ApplicationConstants.MERCHANTID_LABLE, required = false) final String merchantId) {

		LOGGER.info(" ==> Method : merchantDelete ==> called ");

		ResponseEntity<UIOperationResponse> responseEntity = null;

		try {

			if (!ApplicationUtils.isEmpty(merchantId)) {

				getServiceRegistry().getMerchantService().inActiveMerchant(Boolean.FALSE, UUID.fromString(merchantId));

				responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_SUCCESS,
						new HashSet<>(Arrays.asList(ApplicationConstants.MERCHANT_MODULE_LABEL
								+ getMessageSource().getMessage(ErrorDataEnum.DELETE_SUCCESS_MESSAGE.message(), null, null))),
						null, UIResponseCodeEnum.DELETE_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

			} else {
				responseEntity = commonErrorResponse(new UIOperationResponse(),
						new HashSet<>(Arrays.asList(getMessageSource().getMessage(ErrorDataEnum.COMMON_BADREQUEST_ERROR_MESSAGE.message(), null, null))),
						HttpStatus.BAD_REQUEST);
			}
		} catch (final Exception e) {

			logError(LOGGER, merchantId, e, " ==> Method ==> merchantDelete ==> Exception ==> ");
			responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_ERROR,
					new HashSet<>(Arrays.asList(ApplicationConstants.COMMON_ERRORMESSAGE)), null, UIResponseCodeEnum.FAILED_MESSAGE.getCode()),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return responseEntity;
	}

	/**
	 * The method <code>addMerchant</code> is responsible for save the merchant data in the database.
	 *
	 * @param merchant
	 * @return
	 */
	@PostMapping(value = MERCHANT_ADD_URL)
	public ResponseEntity<UIOperationResponse> addMerchant(@RequestBody(required = false) final Merchant merchant) {

		LOGGER.info(" ==> Method : addMerchant ==> called ");

		ResponseEntity<UIOperationResponse> responseEntity = null;

		try {

			final HashSet<String> errors = validateMerchant(merchant, Boolean.TRUE);

			if (!ApplicationUtils.isValid(errors)) {

				final Merchant merchantData = getServiceRegistry().getMerchantService().saveMerchant(merchant);

				responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_SUCCESS,
						new HashSet<>(Arrays.asList(ApplicationConstants.MERCHANT_MODULE_LABEL
								+ getMessageSource().getMessage(ErrorDataEnum.ADD_SUCCESS_MESSAGE.message(), null, null))),
						merchantData, UIResponseCodeEnum.ADD_SUCCESS_MESSAGE.getCode()), HttpStatus.OK);

			} else {

				responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_ERROR, new LinkedHashSet<String>(errors), null,
						UIResponseCodeEnum.FAILED_MESSAGE.getCode()), HttpStatus.BAD_REQUEST);

			}
		} catch (final Exception e) {

			logError(LOGGER, null, e, " ==> Method ==> addMerchant ==> Exception ==> ");
			responseEntity = new ResponseEntity<>(getUiOperationResponse(ApplicationConstants.STATUS_ERROR,
					new HashSet<>(Arrays.asList(ApplicationConstants.COMMON_ERRORMESSAGE)), null, UIResponseCodeEnum.FAILED_MESSAGE.getCode()),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return responseEntity;
	}

	/**
	 * This method <code>getMerchantListByUserId</code> is used for get the merchant list based on userId params.
	 *
	 * @param userId
	 * @return
	 */
	private List<MerchantDto> getMerchantListByUserId(final String userId) {

		LOGGER.info(" ==> Method : getMerchantListByUserId ==> Enter ");

		final List<MerchantDto> merchantDtoList = new ArrayList<>();

		final List<Merchant> merchantList = getServiceRegistry().getMerchantService().getAllMerchantsByUserId(userId);

		if (ApplicationUtils.isValid(merchantList)) {

			for (final Merchant merchant : merchantList) {

				final MerchantDto merchantDto = new MerchantDto();
				BeanUtils.copyProperties(merchant, merchantDto);

				final ApplicationUser userById = getServiceRegistry().getUserService().getUserById(merchant.getUserId());

				if (userById != null && !ApplicationUtils.isEmpty(userById.getName())) {
					merchantDto.setUserName(userById.getName());
				}

				merchantDtoList.add(merchantDto);
			}
		}

		LOGGER.info(" ==> Method : getMerchantListByUserId ==> Exit ");
		return merchantDtoList;
	}

	/**
	 * The method <code>validateMerchant</code> is responsible for validate the merchant details.
	 *
	 * @param merchant
	 * @param isAdd
	 * @return
	 */
	private HashSet<String> validateMerchant(final Merchant merchant, final Boolean isAdd) {

		LOGGER.info(" ==> Method : validateMerchant ==> Enter ");

		final HashSet<String> errors = new HashSet<>();

		if (merchant.getUserId() == null) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.USER_SELECT_ERROR_MESSAGE.message(), null, null));
		}

		if (ApplicationUtils.isEmpty(merchant.getMerchantname())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.MERCHANT_NAME_ERROR_MESSAGE.message(), null, null));
		} else {

			if (!ApplicationUtils.isOnlyAlphaAndSpace(merchant.getMerchantname())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ALPHAANDSPACE.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.MERCHANT_NAME_ERROR.message(), null, null));
			} else {
				if (isAdd && ApplicationUtils.isValid(getServiceRegistry().getMerchantService().getAllMerchantsByUserId(String.valueOf(merchant.getUserId()))
						.stream().map(Merchant::getMerchantname).collect(Collectors.toList()).stream()
						.filter(a -> a.equalsIgnoreCase(merchant.getMerchantname())).collect(Collectors.toList()))) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.MERCHANT_NAME_ERROR.message(), null, null) }, null));
				} else {
					if (!isAdd
							&& !getServiceRegistry().getMerchantService().getMerchantByMerchantId(merchant.getMerchantId()).getMerchantname()
									.equalsIgnoreCase(merchant.getMerchantname())
							&& ApplicationUtils.isValid(getServiceRegistry().getMerchantService().getAllMerchantsByUserId(String.valueOf(merchant.getUserId()))
									.stream().map(Merchant::getMerchantname).collect(Collectors.toList()).stream()
									.filter(a -> a.equalsIgnoreCase(merchant.getMerchantname())).collect(Collectors.toList()))) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
								new Object[] { getMessageSource().getMessage(ErrorDataEnum.MERCHANT_NAME_ERROR.message(), null, null) }, null));
					}
				}
			}
		}

		if (ApplicationUtils.isEmpty(merchant.getSourcemerchantid())) {
			errors.add(getMessageSource().getMessage(ErrorDataEnum.SOURCE_MERCHANT_ID_ERROR_MESSAGE.message(), null, null));
		} else {

			if (!ApplicationUtils.isOnlyNumber(merchant.getSourcemerchantid())) {
				errors.add(getMessageSource().getMessage(ErrorDataEnum.ERROR_ONLY_INTEGER.message(), null, null)
						+ getMessageSource().getMessage(ErrorDataEnum.SOURCE_MERCHANT_ID_ERROR.message(), null, null));
			} else {
				if (isAdd && getServiceRegistry().getMerchantService().getAllMerchantsByUserId(String.valueOf(merchant.getUserId())).stream()
						.map(Merchant::getSourcemerchantid).collect(Collectors.toList()).contains(merchant.getSourcemerchantid())) {
					errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
							new Object[] { getMessageSource().getMessage(ErrorDataEnum.SOURCE_MERCHANT_ID_ERROR.message(), null, null) }, null));
				} else {
					if (!isAdd
							&& !getServiceRegistry().getMerchantService().getMerchantByMerchantId(merchant.getMerchantId()).getSourcemerchantid()
									.equals(merchant.getSourcemerchantid())
							&& getServiceRegistry().getMerchantService().getAllMerchantsByUserId(String.valueOf(merchant.getUserId())).stream()
									.map(Merchant::getSourcemerchantid).collect(Collectors.toList()).contains(merchant.getSourcemerchantid())) {
						errors.add(getMessageSource().getMessage(ErrorDataEnum.COMMON_EXISTS_ERROR_MESSAGE.message(),
								new Object[] { getMessageSource().getMessage(ErrorDataEnum.SOURCE_MERCHANT_ID_ERROR.message(), null, null) }, null));
					}
				}
			}
		}

		LOGGER.info(" ==> Method : validateMerchant ==> Exit ");

		return errors;

	}

}
