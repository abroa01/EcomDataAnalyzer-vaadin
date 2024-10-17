package com.acs560.dataanalyzer.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acs560.dataanalyzer.models.Customer;
import com.acs560.dataanalyzer.models.EcomSalesData;
import com.acs560.dataanalyzer.repositories.CustomerRepository;
import com.acs560.dataanalyzer.repositories.EcomSalesDataRepository;
import com.acs560.dataanalyzer.services.EcomSalesDataService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for operations on EcomSalesData.
 * Provides methods to retrieve, filter, add, update, and delete sales data.
 */
@Service
@RequiredArgsConstructor
public class EcomSalesDataServiceImpl implements EcomSalesDataService {

    @Autowired
    private final EcomSalesDataRepository ecomSalesDataRepository;
    
    @Autowired
    private final CustomerRepository customerRepository;

    /**
     * Retrieves a single sales data entry by its index.
     *
     * @param index - the unique index of the sales data.
     * @return the sales data entry or null if not found.
     */
    @Override
    public EcomSalesData getSalesDataByIndex(int index) {
        return ecomSalesDataRepository.findById(index).orElse(null);
    }

    /**
     * Retrieves all sales data entries.
     *
     * @return the list of all sales data.
     */
    @Override
    public List<EcomSalesData> getAllSalesData() {
        return (List<EcomSalesData>) ecomSalesDataRepository.findAll();
    }

    /**
     * Retrieves sales data entries filtered by category.
     *
     * @param category - the category to filter by.
     * @return the list of sales data filtered by category.
     */
    @Override
    public List<EcomSalesData> getSalesDataByCategory(String category) {
        return getAllSalesData().stream()
                .filter(data -> data.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves sales data entries filtered by a specific date.
     *
     * @param date - the date to filter by.
     * @return the list of sales data filtered by the given date.
     */
    @Override
    public List<EcomSalesData> getSalesDataByDate(Date date) {
        return getAllSalesData().stream()
                .filter(data -> data.getDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves sales data entries filtered by order status.
     *
     * @param status - the status of the sales data (e.g., 'Shipped', 'Cancelled').
     * @return the list of sales data filtered by status.
     */
    @Override
    public List<EcomSalesData> getSalesDataByStatus(String status) {
        return getAllSalesData().stream()
                .filter(data -> data.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves sales data entries filtered by fulfilment type.
     *
     * @param fulfilment - the fulfilment type to filter by (e.g., 'Amazon', 'Merchant').
     * @return the list of sales data filtered by fulfilment type.
     */
    @Override
    public List<EcomSalesData> getSalesDataByFulfilment(String fulfilment) {
        return getAllSalesData().stream()
                .filter(data -> data.getFulfilment().equalsIgnoreCase(fulfilment))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves sales data entries filtered by sales channel.
     *
     * @param channel - the sales channel to filter by.
     * @return the list of sales data filtered by sales channel.
     */
    @Override
    public List<EcomSalesData> getSalesDataByChannel(String channel) {
        return getAllSalesData().stream()
                .filter(data -> data.getChannel().equalsIgnoreCase(channel))
                .collect(Collectors.toList());
    }

    /**
     * Filters sales data entries based on multiple parameters such as status, amount range,
     * date range, and location.
     *
     * @param status - the order status.
     * @param minAmount - the minimum sales amount.
     * @param maxAmount - the maximum sales amount.
     * @param startDate - the start date.
     * @param endDate - the end date.
     * @param city - the shipping city.
     * @param state - the shipping state.
     * @return the list of filtered sales data entries matching the criteria.
     */
    @Override
    public List<EcomSalesData> filterSalesData(String status, Double minAmount, Double maxAmount,
                                               Date startDate, Date endDate, String city, String state) {
        return getAllSalesData().stream()
                .filter(data -> status == null || data.getStatus().equalsIgnoreCase(status))
                .filter(data -> minAmount == null || data.getAmount() >= minAmount)
                .filter(data -> maxAmount == null || data.getAmount() <= maxAmount)
                .filter(data -> startDate == null || !data.getDate().before(startDate))
                .filter(data -> endDate == null || !data.getDate().after(endDate))
                .filter(data -> city == null || data.getShipCity().equalsIgnoreCase(city))
                .filter(data -> state == null || data.getShipState().equalsIgnoreCase(state))
                .collect(Collectors.toList());
    }

    /**
     * Adds new sales data entry to the database.
     *
     * @param newData the sales data to be added.
     * @return true if data was successfully added, false otherwise.
     */
    public boolean addSalesData(EcomSalesData newData) {
        if (newData != null && !ecomSalesDataRepository.existsByOrderId(newData.getOrderId())) {
            Customer customer = customerRepository.findById(newData.getCustomer().getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
            newData.setCustomer(customer);
            ecomSalesDataRepository.save(newData);
            return true;
        }
        return false;
    }

    /**
     * Updates an existing sales data entry.
     *
     * @param updatedData the sales data with updated information.
     * @return true if data was successfully updated, false otherwise.
     */
    @Override
    public boolean updateSalesData(EcomSalesData updatedData) {
        if (updatedData != null && ecomSalesDataRepository.existsById(updatedData.getRecordIndex())) {
            ecomSalesDataRepository.save(updatedData);
            return true;
        }
        return false;
    }

    /**
     * Deletes a sales data entry by index.
     *
     * @param index the index of the sales data to be deleted.
     * @return true if data was successfully deleted, false otherwise.
     */
    @Override
    public boolean deleteSalesData(int index) {
        if (ecomSalesDataRepository.existsById(index)) {
            ecomSalesDataRepository.deleteById(index);
            return true;
        }
        return false;
    }


}
