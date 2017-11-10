package org.grails.twitter

import net.sf.jasperreports.engine.JRExporter
import net.sf.jasperreports.engine.JRExporterParameter
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.export.JRPdfExporter

class ReportController {

    def personService

    def index() {
        print();
    }

    def print() {
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        try {
            String reportName, fileName, dotJasper
            fileName = "StatusReport"
            reportName = getAbsoluteReportFilePath('reports/' + fileName + '.jrxml')
            dotJasper = getAbsoluteReportFilePath('reports/' + fileName + '.jasper')

            // Report parameter
            Map<String, String> reportParam = [:]
            // compiles jrxml
            JasperCompileManager.compileReportToFile(reportName);
            // fills compiled report with parameters and a connection
            JasperPrint print = JasperFillManager.fillReport(dotJasper, reportParam, new JRBeanCollectionDataSource(personService.getPersonInstanceList()));

            // exports report to pdf
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, pdfStream); // your output goes here

            exporter.exportReport();

            render(file: pdfStream.toByteArray(), contentType: 'application/pdf')

        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } finally {
            pdfStream.close()
        }
    }

    private String getAbsoluteReportFilePath(String fileName) {
        grailsApplication.mainContext.getResource(fileName).file.getAbsoluteFile()
    }

}
