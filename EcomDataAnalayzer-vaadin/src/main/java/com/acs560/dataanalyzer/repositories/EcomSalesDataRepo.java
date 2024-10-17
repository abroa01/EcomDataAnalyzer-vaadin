package com.acs560.dataanalyzer.repositories;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.acs560.dataanalyzer.models.EcomSalesData;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

@Repository
public class EcomSalesDataRepo {

    private static final String CSV_DELIMITER = ",";
    private static final String INPUT_FILE_PATH = "AmazonSalesReport.csv";
    private static final String CSV_PATH = "output_sales_data.csv";

    @Getter
    private static final List<EcomSalesData> salesData = new ArrayList<>();

    @PostConstruct
    public void init() {
        readFile(INPUT_FILE_PATH);
    }

    /**
     * Reads the sales data from the CSV file and populates the internal data structure.
     *
     * @param fileName the name of the CSV file to read from
     */
    public void readFile(String fileName) {
        try (BufferedReader csvReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            csvReader.readLine(); // Skip header
            while ((line = csvReader.readLine()) != null) {
                String[] values = line.split(CSV_DELIMITER);
                addSalesData(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds new sales data if not already present.
     * Rolls back if CSV save fails.
     *
     * @param values the sales data to add as an array of strings
     * @return true if successfully added and saved to CSV, false otherwise
     */
    public boolean addSalesData(String[] values) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        try {
            Date date = dateFormat.parse(values[2]);
            EcomSalesData newData = new EcomSalesData(
                    Integer.parseInt(values[0]),  // index
                    values[1],                    // orderId
                    date,                         // date
                    values[3],                    // status
                    values[4],                    // fulfilment
                    values[5],                    // channel
                    values[6],                    // category
                    values[7],                    // size
                    Double.parseDouble(values[8]),// amount
                    values[9],                    // shipCity
                    values[10]                    // shipState
, null
                );

            if (!isDuplicate(newData)) {
                salesData.add(newData);   // Add to in-memory data structure
                return appendSalesData(newData);  // Append only new data to CSV
            }
        } catch (ParseException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates existing sales data. Rolls back if CSV save fails.
     *
     * @param updatedData the updated sales data
     * @return true if successfully updated and saved to CSV, false otherwise
     */
    public boolean updateSalesData(EcomSalesData updatedData) {
        EcomSalesData existingData = findSalesDataByIndex(updatedData.getRecordIndex());
        if (existingData != null) {
            int index = salesData.indexOf(existingData);
            salesData.set(index, updatedData);

            boolean isSaved = replaceSalesData(salesData);
            if (!isSaved) {
                salesData.set(index, existingData); // Rollback
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Deletes sales data by index. Rolls back if CSV save fails.
     *
     * @param index the index of the sales data to delete
     * @return true if successfully deleted and saved to CSV, false otherwise
     */
    public boolean deleteSalesData(int index) {
        EcomSalesData existingData = findSalesDataByIndex(index);
        if (existingData != null) {
            salesData.remove(existingData);

            boolean isSaved = replaceSalesData(salesData);
            if (!isSaved) {
                salesData.add(existingData); // Rollback
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Checks if sales data is a duplicate.
     *
     * @param data the sales data to check
     * @return true if duplicate, false otherwise
     */
    private boolean isDuplicate(EcomSalesData data) {
        return salesData.stream()
                .anyMatch(existingData -> existingData.equals(data));
    }

    /**
     * Finds sales data by its index.
     *
     * @param index the index of the sales data
     * @return the sales data object if found, null otherwise
     */
    private EcomSalesData findSalesDataByIndex(int index) {
        return salesData.stream()
                .filter(data -> data.getRecordIndex() == index)
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves the list of sales data to the CSV file. Can either append or overwrite.
     *
     * @param salesData the list of sales data to save
     * @param appendMode true to append, false to overwrite
     * @return true if successfully saved, false otherwise
     */
    public boolean storeSalesDataToCsv(List<EcomSalesData> salesData, boolean appendMode) {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(INPUT_FILE_PATH, appendMode))) {
            for (EcomSalesData entry : salesData) {
                fileWriter.write(convertToCsvFormat(entry));
                fileWriter.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Converts a sales data object to a CSV line.
     *
     * @param entry the sales data object to convert
     * @return the formatted CSV line
     */
    private String convertToCsvFormat(EcomSalesData entry) {
    	SimpleDateFormat csvDateFormat = new SimpleDateFormat("MM/dd/yy");
        String formattedDate = csvDateFormat.format(entry.getDate()); // Ensure consistent date format
        return entry.getRecordIndex() + "," +
               entry.getOrderId() + "," +
               formattedDate + "," +
               entry.getStatus() + "," +
               entry.getFulfilment() + "," +
               entry.getChannel() + "," +
               entry.getCategory() + "," +
               entry.getSize() + "," +
               entry.getAmount() + "," +
               entry.getShipCity() + "," +
               entry.getShipState();
    }

    /**
     * Appends new sales data to the CSV file.
     *
     * @param newData the sales data to append
     * @return true if successfully saved, false otherwise
     */
    public boolean appendSalesData(EcomSalesData newData) {
        return storeSalesDataToCsv(List.of(newData), true);
    }

    /**
     * Replaces the CSV file with the updated sales data list.
     *
     * @param updatedSalesList the updated sales data list
     * @return true if successfully saved, false otherwise
     */
    public boolean replaceSalesData(List<EcomSalesData> updatedSalesList) {
        return storeSalesDataToCsv(updatedSalesList, false);
    }
}
