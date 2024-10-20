package com.acs560.dataanalyzer.views.customer;

import com.acs560.dataanalyzer.models.Customer;
import com.acs560.dataanalyzer.services.CustomerService;
import com.acs560.dataanalyzer.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;

/**
 * The view to display and manage customer data.
 */
@Route(value = "customers", layout = MainLayout.class)
@AnonymousAllowed
@PageTitle("Customers | Data Analyzer")
public class CustomerView extends VerticalLayout {

    private final CustomerService customerService;
    private final Grid<Customer> grid;
    private final CustomerForm form;
    private final TextField filterText;
    private Dialog dialog;

    /**
     * Constructor for CustomerView.
     * 
     * @param customerService - the service to manage customers.
     */
    public CustomerView(CustomerService customerService) {
        this.customerService = customerService;

        addClassName("list-view");
        setSizeFull();

        grid = createGrid();
        form = createForm();
        filterText = createFilter();

        add(getToolbar(), getContent());
        updateGrid();
        closeForm();
    }

    /**
     * Creates the grid component for displaying customer data.
     * 
     * @return the configured Grid component.
     */
    private Grid<Customer> createGrid() {
        Grid<Customer> grid = new Grid<>(Customer.class);
        grid.addClassNames("customer-grid");
        grid.setSizeFull();

        grid.setColumns();
        grid.addColumn(Customer::getName).setHeader("Name").setSortable(true);
        grid.addColumn(Customer::getEmail).setHeader("Email").setSortable(true);

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> handleSelected(event.getValue()));

        return grid;
    }

    /**
     * Creates the form component for adding and editing customers.
     * 
     * @return the configured CustomerForm component.
     */
    private CustomerForm createForm() {
        CustomerForm form = new CustomerForm(customerService);
        form.addListener(CustomerForm.AddEvent.class, this::addCustomer);
        form.addListener(CustomerForm.UpdateEvent.class, this::updateCustomer);
        form.addListener(CustomerForm.DeleteEvent.class, this::deleteCustomer);
        form.addListener(CustomerForm.CancelEvent.class, e -> closeForm());

        return form;
    }

    /**
     * Creates the text field for filtering customer data.
     * 
     * @return the configured TextField component.
     */
    private TextField createFilter() {
        TextField filterText = new TextField();
        filterText.setPlaceholder("Filter by name or email...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGrid());

        return filterText;
    }

    /**
     * Creates the toolbar containing the filter and add button.
     * 
     * @return the configured toolbar component.
     */
    private Component getToolbar() {
        Button addButton = new Button("Add Customer");
        addButton.addClickListener(click -> handleAdd());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addButton);
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
        content.setSizeFull();
        return content;
    }

    /**
     * Updates the grid with customer data.
     */
    private void updateGrid() {
        String filterValue = filterText.getValue();
        List<Customer> customers = (filterValue == null || filterValue.isEmpty()) ? 
            (List<Customer>) customerService.getAllCustomers() : customerService.getCustomersByNameOrEmail(filterValue);
        grid.setItems(customers);
    }

    /**
     * Handles selection of a customer in the grid.
     * 
     * @param customer - the selected customer.
     */
    private void handleSelected(Customer customer) {
        if (customer != null) {
            showFormInDialog(customer);
        }
    }

    /**
     * Handles the add button action.
     */
    private void handleAdd() {
        grid.asSingleSelect().clear();
        showFormInDialog(new Customer());
    }

    /**
     * Displays the form in a dialog for adding or editing customer data.
     * 
     * @param customer - the customer data to be displayed in the form.
     */
    private void showFormInDialog(Customer customer) {
        dialog = new Dialog();
        form.update(customer, customer.getCustomerId() == null);
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
     * Handles adding a new customer.
     * 
     * @param event - the AddEvent containing the new customer data.
     */
    private void addCustomer(CustomerForm.AddEvent event) {
        customerService.addCustomer(event.getCustomer());
        Notification.show("Customer added");
        updateGrid();
        closeForm();
    }

    /**
     * Handles updating an existing customer.
     * 
     * @param event - the UpdateEvent containing the updated customer data.
     */
    private void updateCustomer(CustomerForm.UpdateEvent event) {
        customerService.updateCustomer(event.getCustomer().getCustomerId(), event.getCustomer());
        Notification.show("Customer updated");
        updateGrid();
        closeForm();
    }

    /**
     * Handles deleting a customer.
     * 
     * @param event - the DeleteEvent containing the customer data to be deleted.
     */
    private void deleteCustomer(CustomerForm.DeleteEvent event) {
        Dialog confirmationDialog = new Dialog();
        confirmationDialog.setHeaderTitle("Confirm Delete");
        confirmationDialog.add("Are you sure you want to delete this customer?");
        Button confirmButton = new Button("Delete", e -> {
            customerService.deleteCustomer(event.getCustomer().getCustomerId());
            Notification.show("Customer deleted");
            updateGrid();
            closeForm();
            confirmationDialog.close();
        });
        Button cancelButton = new Button("Cancel", e -> confirmationDialog.close());
        confirmationDialog.add(new HorizontalLayout(confirmButton, cancelButton));
        confirmationDialog.open();
    }
}
