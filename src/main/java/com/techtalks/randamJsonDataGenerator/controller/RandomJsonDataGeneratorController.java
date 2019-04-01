package com.techtalks.randamJsonDataGenerator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techtalks.randamJsonDataGenerator.exception.RandomJsonGeneratorException;
import com.techtalks.randamJsonDataGenerator.service.RandomJsonDataGeneratorService;
import com.techtalks.randamJsonDataGenerator.utils.Constants;
import com.techtalks.randamJsonDataGenerator.utils.DataType;
import com.techtalks.randamJsonDataGenerator.utils.ResponseParser;

/**
 * 
 * @author sramveer
 *
 */

@RestController
@RequestMapping(value = "/v1/dataGenerator")
public class RandomJsonDataGeneratorController {
	@Autowired
	private ResponseParser responseParser;
	@Autowired
	private RandomJsonDataGeneratorService service;

	/**
	 * logger object to log the operations
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Generate data for recordCount and of type dataType
	 * 
	 * @param jsonBody
	 * @param recordCount
	 * @param dataType
	 * @return
	 */
	@RequestMapping(value = "/generateData", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Object> generateData(@RequestBody String jsonBody,
			@RequestParam(value = "recordCount") Integer recordCount, @RequestParam(value = "dataType") String dataType,
			@RequestParam(value = "randomValues") boolean randomValues, @RequestHeader(value = "xpath") String xpath) {
		String jsonString = Constants.EMPTY_STRING;
		try {
			this.logger.info("input params : dataType {}, xpath: {},recordCount: {} ", dataType, xpath, recordCount);
			validateInput(jsonBody, recordCount, dataType, xpath);
			jsonString = this.service.generateData(jsonBody, recordCount, dataType, xpath, randomValues);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (RandomJsonGeneratorException ex) {
			return new ResponseEntity<>(
					this.responseParser.build(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(jsonString, HttpStatus.OK);
	}

	/**
	 * Validate input
	 * 
	 * @param jsonBody
	 * @param recordCount
	 * @param dataType
	 */
	private void validateInput(String jsonBody, Integer recordCount, String dataType, String xpath) {
		try {
			Assert.hasLength(jsonBody, "RequestBody must Not be empty");
			Assert.hasLength(dataType, "dataType must not be empty");
			Assert.isTrue(recordCount > 0, "The value must be greater than zero");
			Assert.isTrue(dataType.equals(DataType.JSON.toString()) || dataType.equals(DataType.CSV.toString()),
					"DataType format is not supported");
		} catch (IllegalArgumentException e) {
			this.logger.error("Any one/all of the input is wrong  ", e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}

	}

}
