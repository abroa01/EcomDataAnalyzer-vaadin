/**
 * Interface for operations on EcomSalesData. Provides methods to retrieve and filter sales data
 * based on various criteria like category, date, status, fulfilment type, and channel.
 */
package com.acs560.dataanalyzer.services;

import java.util.Date;
import java.util.List;
import com.acs560.dataanalyzer.models.EcomSalesData;

public interface EcomSalesDataService {
    
    /**
     * Get a single sales data entry by its index.
     * 
     * @param index - the index of the sales data.
     * @return - the EcomSalesData.
     */
    EcomSalesData getSalesDataByIndex(int index);
    
    /**
     * Get the list of all sales data.
     * 
     * @return - the list of all sales data.
     */
    List<EcomSalesData> getAllSalesData();
    
    /**
     * Get the sales data filtered by category.
     * 
     * @param category - the category to filter by.
     * @return - the list of sales data filtered by category.
     */
    List<EcomSalesData> getSalesDataByCategory(String category);
    
    /**
     * Get the sales data filtered by date.
     * 
     * @param date - the date to filter by.
     * @return - the list of sales data filtered by date.
     */
    List<EcomSalesData> getSalesDataByDate(Date date);
    
    /**
     * Get the sales data filtered by status (e.g., 'Shipped', 'Cancelled').
     * 
     * @param status - the status to filter by.
     * @return - the list of sales data filtered by status.
     */
    List<EcomSalesData> getSalesDataByStatus(String status);
    
    /**
     * Get the sales data filtered by fulfilment type.
     * 
     * @param fulfilment - the fulfilment type to filter by (e.g., 'Amazon', 'Merchant').
     * @return - the list of sales data filtered by fulfilment type.
     */
    List<EcomSalesData> getSalesDataByFulfilment(String fulfilment);
    
    /**
     * Get the sales data filtered by sales channel.
     * 
     * @param channel - the sales channel to filter by.
     * @return - the list of sales data filtered by channel.
     */
    List<EcomSalesData> getSalesDataByChannel(String channel);
    
    /**
     * Filter sales data based on multiple parameters such as status, amount range, date range, and location.
     * 
     * @param status - the status of the order (optional).
     * @param minAmount - the minimum sales amount (optional).
     * @param maxAmount - the maximum sales amount (optional).
     * @param startDate - the start date of the sales (optional).
     * @param endDate - the end date of the sales (optional).
     * @param city - the shipping city (optional).
     * @param state - the shipping state (optional).
     * @return - the list of filtered sales data.
     */
    List<EcomSalesData> filterSalesData(String status, Double minAmount, Double maxAmount, 
                                        Date startDate, Date endDate, String city, String state);
    
    
    /**
     * Adds new sales data.
     *
     * @param values the sales data to add as an array of strings
     * @return true if the data was successfully added, false otherwise
     */
    boolean addSalesData(EcomSalesData newData);

    /**
     * Updates existing sales data.
     *
     * @param updatedData the updated sales data object
     * @return true if the data was successfully updated, false otherwise
     */
    boolean updateSalesData(EcomSalesData updatedData);

    /**
     * Deletes sales data by index.
     *
     * @param index the index of the sales data to delete
     * @return true if the data was successfully deleted, false otherwise
     */
    boolean deleteSalesData(int index);
}

