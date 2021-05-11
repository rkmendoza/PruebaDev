package com.prueba.dev.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prueba.dev.message.Message;
import com.prueba.dev.message.Response;
import com.prueba.dev.service.CsvFileServices;
import com.prueba.dev.utils.ApacheCommonsCsvUtil;

@RestController
@RequestMapping("/api/upload/csv")
public class UploadCsvRestApi {
	@Autowired
	CsvFileServices csvFileServices;

	@PostMapping("/single")
	public Response uploadSingleCSVFile(@RequestParam("csvfile") MultipartFile csvfile) {
	
		Response response = new Response();
	
		// Checking the upload-file's name before processing
		if (csvfile.getOriginalFilename().isEmpty()) {
			response.addMessage(new Message(csvfile.getOriginalFilename(),
					"No hay archivo seleccionado para cargar", "fail"));
	
			return response;
		}
	
		// checking the upload file's type is CSV or NOT
		
		if(!ApacheCommonsCsvUtil.isCSVFile(csvfile)) { 
		    response.addMessage(new Message(csvfile.getOriginalFilename(), "Error: No es un archivo CSV!", "fail")); 
	        return response; 
		}
		  
		 
		try {
			// save file data to database
			csvFileServices.store(csvfile.getInputStream());
			response.addMessage(new Message(csvfile.getOriginalFilename(), "Archivo cargado con exito!", "ok"));
		} catch (Exception e) {
			response.addMessage(new Message(csvfile.getOriginalFilename(), e.getMessage(), "fail"));
		}
	
		return response;
	}
}