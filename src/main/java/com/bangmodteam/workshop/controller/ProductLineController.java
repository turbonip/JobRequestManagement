package com.bangmodteam.workshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bangmodteam.workshop.entity.ProductLineInfo;
import com.bangmodteam.workshop.repository.ProductLineInfoRepository;

@Controller
@RequestMapping("/productionline")
public class ProductLineController {

	@Autowired
	private ProductLineInfoRepository productLineInfoRepository;

	@RequestMapping(value = "/getByLocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductLineInfo>> getProductLineByLocationId(@RequestParam Long locationId) {

		List<ProductLineInfo> lineList = productLineInfoRepository.findByLocationInfoId(locationId);

		if (lineList == null) {
			lineList = new ArrayList<ProductLineInfo>();
		}

		ResponseEntity<List<ProductLineInfo>> response = new ResponseEntity<List<ProductLineInfo>>(lineList, HttpStatus.OK);
		
		return response;

	}

}
