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
        ByteArrayOutputStream pdfStream = null
        try {
            String reportName, namaFile, dotJasper
            namaFile = "StatusReport"
            reportName = grailsApplication.mainContext.getResource('reports/' + namaFile + '.jrxml').file.getAbsoluteFile()
            dotJasper = grailsApplication.mainContext.getResource('reports/' + namaFile + '.jasper').file.getAbsoluteFile()

            // Report parameter
            Map<String, String> reportParam = new HashMap<String, String>()
            // compiles jrxml
            JasperCompileManager.compileReportToFile(reportName);
            // fills compiled report with parameters and a connection
            JasperPrint print = JasperFillManager.fillReport(dotJasper, reportParam, new JRBeanCollectionDataSource(personService.personInstanceList()));

            pdfStream = new ByteArrayOutputStream();

            // exports report to pdf
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, pdfStream); // your output goes here

            exporter.exportReport();

        } catch (Exception e) {

            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        } finally {
            if (pdfStream != null) {
                render(file: pdfStream.toByteArray(), contentType: 'application/pdf')
                pdfStream.close()
            }
        }
    }

}
