package br.edu.fateczl.F1JasperLocalBD.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.fateczl.F1JasperLocalBD.persistence.GenericDao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class RelatorioController {
	
	@Autowired
	GenericDao gDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(name = "relatorio", value = "/relatorio", method = RequestMethod.POST)
	public ResponseEntity geraRelatorio(@RequestParam Map<String, String> params) {
		String erro = "";
		String pais = params.get("pais");

		Map<String, Object> paramsEntradaRelatorio = new HashMap<>();
		paramsEntradaRelatorio.put("pais", pais);
		
		byte[] bytes = null;
		HttpHeaders header = new HttpHeaders();
		HttpStatus status = null;
		InputStreamResource resource = null;
		
		try {
			Connection conn = gDao.getConnection();
			File arquivo = ResourceUtils.getFile
					("classpath:reports/F12014LabBD20222.jasper");
			JasperReport report = 
					(JasperReport) JRLoader
					.loadObjectFromFile(arquivo.getAbsolutePath());
			bytes = JasperRunManager
					.runReportToPdf(report, paramsEntradaRelatorio, conn);
		} catch (ClassNotFoundException | SQLException | FileNotFoundException | JRException e) {
			e.printStackTrace();
			erro = e.getMessage();
			status = HttpStatus.BAD_GATEWAY;
		} finally {
			if (erro != null) {
				status = HttpStatus.OK;
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				resource = new InputStreamResource(bais);
				header.setContentType(MediaType.APPLICATION_PDF);
				header.setContentLength(bytes.length);
			}
		}
		
		return new ResponseEntity(resource, header, status);
	}
}
