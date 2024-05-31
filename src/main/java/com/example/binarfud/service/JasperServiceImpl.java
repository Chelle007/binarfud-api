package com.example.binarfud.service;

import com.example.binarfud.model.dto.order.OrderReceiptDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperServiceImpl implements JasperService {
    @Override
    public byte[] getOrderReport(OrderReceiptDto orderReceiptDto, String format) {
        JasperReport jasperReport;
        try {
            jasperReport = (JasperReport) JRLoader
                    .loadObject(ResourceUtils.getFile("order-report.jasper"));
        } catch (JRException | FileNotFoundException e) {
            try {
                File file = ResourceUtils.getFile("classpath:jasper/order-report.jrxml");
                jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                JRSaver.saveObject(jasperReport, "order-report.jasper");
            } catch (FileNotFoundException | JRException ex) {
                throw new RuntimeException(ex);
            }
        }

        JRBeanCollectionDataSource orderDetailDataset = new JRBeanCollectionDataSource(orderReceiptDto.getOrderDetailReportDtoList());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", String.valueOf(orderReceiptDto.getOrderId()));
        parameters.put("orderTime", String.valueOf(orderReceiptDto.getOrderTime()));
        parameters.put("restaurantName", String.valueOf(orderReceiptDto.getRestaurantName()));
        parameters.put("destinationAddress", String.valueOf(orderReceiptDto.getDestinationAddress()));
        parameters.put("totalQty", String.valueOf(orderReceiptDto.getTotalQty()));
        parameters.put("totalPrice", String.valueOf(orderReceiptDto.getTotalPrice()));
        parameters.put("orderDetailDataset", orderDetailDataset);

        JasperPrint jasperPrint;
        byte[] reportContent;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            switch (format){
                case "pdf" -> reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
                case "xml" -> reportContent = JasperExportManager.exportReportToXml(jasperPrint).getBytes();
                default -> throw new RuntimeException();
            }
        } catch (JRException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return reportContent;
    }
}
