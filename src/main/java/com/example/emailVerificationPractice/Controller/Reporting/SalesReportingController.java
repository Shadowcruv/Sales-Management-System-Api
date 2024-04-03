package com.example.emailVerificationPractice.Controller.Reporting;

import com.example.emailVerificationPractice.Entity.Helper.SaleClientProjection;
import com.example.emailVerificationPractice.Service.ClientServiceImpl;
import com.example.emailVerificationPractice.Service.SalesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

@RequestMapping("/sales-report")
@RestController
public class SalesReportingController {
    private final ClientServiceImpl clientServiceImpl;
    private final SalesServiceImpl salesServiceImpl;

    @Autowired
    public SalesReportingController(ClientServiceImpl clientServiceImpl, SalesServiceImpl salesServiceImpl) {
        this.clientServiceImpl = clientServiceImpl;
        this.salesServiceImpl = salesServiceImpl;
    }


    @GetMapping
    public ResponseEntity<Map<String, Object>> generateSalesReport(@RequestParam("startDate") String firstDate,
                                                                    @RequestParam("endDate") String lastDate){
        try {
            LocalDate startDate = LocalDate.parse(firstDate);
            LocalDate endDate = LocalDate.parse(lastDate);

            Map<String, Object> report = new HashMap<>();

            report.put("totalNumberOfSales", salesServiceImpl.totalSalesBetweenTheDates(startDate, endDate));
            report.put("totalRevenue", salesServiceImpl.totalRevenueBetweenDates(startDate, endDate));
            report.put("topSellingProducts", salesServiceImpl.topSellingProducts());
            report.put("topPerformingSellers", salesServiceImpl.topPerformingSellers(startDate, endDate));

            return new ResponseEntity<>(report, HttpStatus.OK);
        }catch (DateTimeParseException e)   {
            Map<String, Object> report = new HashMap<>();
            report.put("error message: ", "Invalid date format. Please provide dates in ISO format (YYYY-MM-DD)." );
            return new ResponseEntity<>(report, HttpStatus.BAD_REQUEST);

        }catch (Exception e){
            //other exceptions
            Map<String, Object> report = new HashMap<>();
            report.put("error message: ", "An error occurred while generating the sales report." );
            return new ResponseEntity<>(report, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
