package com.example.binarfud.service;

import com.example.binarfud.model.dto.order.OrderReceiptDto;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Service;

@Service
public interface JasperService {
    byte[] getOrderReport(OrderReceiptDto orderReceiptDto, String format) throws JRException;
}
