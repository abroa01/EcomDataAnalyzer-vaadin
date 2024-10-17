/**
 * 
 */
package com.acs560.dataanalyzer.repositories;

import org.springframework.data.repository.CrudRepository;
import com.acs560.dataanalyzer.models.EcomSalesData;
import java.util.Optional;

public interface EcomSalesDataRepository extends CrudRepository<EcomSalesData, Integer> {

	boolean existsByOrderId(String orderId);

}