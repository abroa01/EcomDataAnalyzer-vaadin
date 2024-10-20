package com.acs560.dataanalyzer.views.ecomsalesdata;

import com.acs560.dataanalyzer.models.EcomSalesData;
import com.acs560.dataanalyzer.services.CustomerService;
import com.acs560.dataanalyzer.services.EcomSalesDataService;
import com.acs560.dataanalyzer.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * The view to display and manage e-commerce sales data.
 */
@SpringComponent
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("EcomSales | Data Analyzer")
public class EcomSalesDataView extends VerticalLayout {

    private final EcomSalesDataService service;
    private final CustomerService customerService;
    private final Grid<EcomSalesData> grid;
    private final EcomSalesDataForm form;
    private final TextField filterText;
    private final ComboBox<String> filterType;
    private Dialog dialog;

    /**
     * Constructor for EcomSalesDataView.
     * 
     * @param service - the service to manage e-commerce sales data.
     */
    @Autowired
    public EcomSalesDataView(EcomSalesDataService service, CustomerService customerService) {
        this.service = service;
        this.customerService = customerService;

        addClassName("list-view");
        setSizeFull();

        grid = createGrid();
        form = createForm();
        filterText = createFilter();
        filterType = createFilterTypeComboBox();

        add(getToolbar(), getContent());
        updateGrid();
        closeForm();
    }

    /**
     * Creates the grid component for displaying sales data.
     * 
     * @return the configured Grid component.
     */
    private Grid<EcomSalesData> createGrid() {
        Grid<EcomSalesData> grid = new Grid<>(EcomSalesData.class);
        grid.addClassNames("ecomsales-grid");
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        grid.setColumns();
        grid.addColumn(EcomSalesData::getOrderId).setHeader("Order ID").setSortable(true);
        grid.addColumn(EcomSalesData::getAmount).setHeader("Amount").setSortable(true);
        grid.addColumn(EcomSalesData::getShipCity).setHeader("Ship City").setSortable(true);
        grid.addColumn(EcomSalesData::getStatus).setHeader("Status").setSortable(true);
        grid.addColumn(EcomSalesData::getChannel).setHeader("Channel").setSortable(true);
        grid.addColumn(EcomSalesData::getCustomer).setHeader("Customer").setSortable(true);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> handleSelected(event.getValue()));

        return grid;
    }

    /**
     * Creates the form component for adding and editing sales data.
     * 
     * @return the configured EcomSalesDataForm component.
     */
    private EcomSalesDataForm createForm() {
        EcomSalesDataForm form = new EcomSalesDataForm(service, customerService);
        form.addListener(EcomSalesDataForm.AddEvent.class, this::addSalesData);
        form.addListener(EcomSalesDataForm.UpdateEvent.class, this::updateSalesData);
        form.addListener(EcomSalesDataForm.DeleteEvent.class, this::deleteSalesData);
        form.addListener(EcomSalesDataForm.CancelEvent.class, e -> closeForm());

        return form;
    }

    /**
     * Creates the text field for filtering sales data.
     * 
     * @return the configured TextField component.
     */
    private TextField createFilter() {
        TextField filterText = new TextField();
        filterText.setPlaceholder("Enter filter value...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGrid());

        return filterText;
    }

    /**
     * Creates the ComboBox for selecting the filter type.
     * 
     * @return the configured ComboBox component.
     */
    private ComboBox<String> createFilterTypeComboBox() {
        ComboBox<String> filterType = new ComboBox<>();
        filterType.setItems("Order ID", "Status", "Ship City", "Channel");
        filterType.setPlaceholder("Select Filter Type");
        filterType.setClearButtonVisible(true);
        filterType.addValueChangeListener(event -> updateGrid());

        return filterType;
    }

    /**
     * Creates the toolbar containing the filter and add button.
     * 
     * @return the configured toolbar component.
     */
    private Component getToolbar() {
        Button addSalesDataButton = new Button("Add Sales Data");
        addSalesDataButton.addClickListener(click -> handleAdd());

        HorizontalLayout toolbar = new HorizontalLayout(filterType, filterText, addSalesDataButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    /**
     * Creates the main content layout for the view.
     * 
     * @return the configured HorizontalLayout component.
     */
    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    /**
     * Updates the grid with data based on filter criteria.
     */
    private void updateGrid() {
        String filterValue = filterText.getValue();
        String filterCriteria = filterType.getValue();

        List<EcomSalesData> filteredData;
        if (filterValue == null || filterValue.isEmpty() || filterCriteria == null) {
            filteredData = service.getAllSalesData();
        } else {
            switch (filterCriteria) {
                
                case "Status":
                    filteredData = service.getSalesDataByStatus(filterValue);
                    break;
                case "Channel":
                    filteredData = service.getSalesDataByChannel(filterValue);
                    break;
                default:
                    filteredData = service.getAllSalesData();
            }
        }
        grid.setItems(filteredData);
    }

    /**
     * Handles selection of a sales data item in the grid.
     * 
     * @param salesData - the selected sales data.
     */
    private void handleSelected(EcomSalesData salesData) {
        if (salesData != null) {
            showFormInDialog(salesData);
        }
    }

    /**
     * Handles the add button action.
     */
    private void handleAdd() {
        grid.asSingleSelect().clear();
        showFormInDialog(new EcomSalesData());
    }

    /**
     * Displays the form in a dialog for adding or editing sales data.
     * 
     * @param salesData - the sales data to be displayed in the form.
     */
    private void showFormInDialog(EcomSalesData salesData) {
        dialog = new Dialog();
        form.update(salesData, salesData.getRecordIndex() == null);
        dialog.add(form);
        dialog.open();
    }

    /**
     * Closes the dialog form.
     */
    private void closeForm() {
        if (dialog != null) {
            dialog.close();
        }
    }

    /**
     * Handles adding a new sales data record.
     * 
     * @param event - the AddEvent containing the new sales data.
     */
    private void addSalesData(EcomSalesDataForm.AddEvent event) {
        service.addSalesData(event.getEcomSalesData());
        Notification.show("Sales data added");
        updateGrid();
        closeForm();
    }

    /**
     * Handles updating an existing sales data record.
     * 
     * @param event - the UpdateEvent containing the updated sales data.
     */
    private void updateSalesData(EcomSalesDataForm.UpdateEvent event) {
        service.updateSalesData(event.getEcomSalesData());
        Notification.show("Sales data updated");
        updateGrid();
        closeForm();
    }

    /**
     * Handles deleting a sales data record.
     * 
     * @param event - the DeleteEvent containing the sales data to be deleted.
     */
    private void deleteSalesData(EcomSalesDataForm.DeleteEvent event) {
        Dialog confirmationDialog = new Dialog();
        confirmationDialog.setHeaderTitle("Confirm Delete");
        confirmationDialog.add("Are you sure you want to delete this record?");
        Button confirmButton = new Button("Delete", e -> {
            service.deleteSalesData(event.getEcomSalesData().getRecordIndex());
            Notification.show("Sales data deleted");
            updateGrid();
            closeForm();
            confirmationDialog.close();
        });
        Button cancelButton = new Button("Cancel", e -> confirmationDialog.close());
        confirmationDialog.add(new HorizontalLayout(confirmButton, cancelButton));
        confirmationDialog.open();
    }
}
