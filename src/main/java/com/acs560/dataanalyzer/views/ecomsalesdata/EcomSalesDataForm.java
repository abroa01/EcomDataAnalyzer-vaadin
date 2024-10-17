package com.acs560.dataanalyzer.views.ecomsalesdata;

import com.acs560.dataanalyzer.models.Customer;
import com.acs560.dataanalyzer.models.EcomSalesData;
import com.acs560.dataanalyzer.services.CustomerService;
import com.acs560.dataanalyzer.services.EcomSalesDataService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.List;

/**
 * The form to manage e-commerce sales data.
 */
public class EcomSalesDataForm extends FormLayout {

    private TextField orderId = new TextField("Order ID");
    private TextField amount = new TextField("Amount");
    private TextField shipCity = new TextField("Ship City");
    private TextField status = new TextField("Status");
    private TextField channel = new TextField("Channel");
    private ComboBox<Customer> customerComboBox = new ComboBox<>("Customer");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button cancel = new Button("Cancel");

    private Binder<EcomSalesData> binder = new BeanValidationBinder<>(EcomSalesData.class);
    private EcomSalesData ecomSalesData;
    private boolean isAdd;

    /**
     * Constructor to create the sales data form.
     * 
     * @param service - the service to manage e-commerce sales data.
     * @param customerService - the service to fetch customers.
     */
    public EcomSalesDataForm(EcomSalesDataService service, CustomerService customerService) {
        addClassName("ecomsales-form");

        // Load customers into the ComboBox
        List<Customer> customers = (List<Customer>) customerService.getAllCustomers();
        customerComboBox.setItems(customers);
        customerComboBox.setItemLabelGenerator(Customer::getName);

        binder.bindInstanceFields(this);

        add(orderId, amount, shipCity, status, channel, customerComboBox, createButtonsLayout());
        setWidth("25em");
    }

    /**
     * Creates the layout for the buttons (save, delete, cancel).
     * 
     * @return a HorizontalLayout containing the buttons.
     */
    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> handleSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(event -> fireEvent(new CancelEvent(this)));

        // Enable save button only when the form is valid
        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, cancel);
    }

    /**
     * Handles the save action. It checks if the form is in add or update mode and fires the appropriate event.
     */
    private void handleSave() {
        try {
            binder.writeBean(ecomSalesData);
            
            // Set the selected customer in the sales data
            Customer selectedCustomer = customerComboBox.getValue();
            if (selectedCustomer == null) {
                Notification.show("Please select a customer");
                return;
            }
            ecomSalesData.setCustomer(selectedCustomer);

            if (isAdd) {
                fireEvent(new AddEvent(this, ecomSalesData));
            } else {
                fireEvent(new UpdateEvent(this, ecomSalesData));
            }
        } catch (ValidationException e) {
            Notification.show("Unable to save sales data");
        }
    }

    /**
     * Updates the form with the given sales data and mode (add or update).
     * 
     * @param ecomSalesData - the sales data to set in the form.
     * @param isAdd - true if the form is for adding a new record; false for updating.
     */
    public void update(EcomSalesData ecomSalesData, boolean isAdd) {
        this.isAdd = isAdd;

        // Hide the delete button if the form is for adding new sales data
        delete.setVisible(!isAdd);

        if (ecomSalesData != null) {
            this.ecomSalesData = ecomSalesData;
            customerComboBox.setValue(ecomSalesData.getCustomer());
        } else {
            // Clear fields if it's a new sales data entry
            orderId.clear();
            amount.clear();
            shipCity.clear();
            status.clear();
            channel.clear();
            customerComboBox.clear();
            this.ecomSalesData = new EcomSalesData();
        }

        binder.setBean(ecomSalesData);
    }

    // Event classes

    /**
     * Abstract event class for the EcomSalesDataForm.
     */
    public static abstract class EcomSalesDataFormEvent extends ComponentEvent<EcomSalesDataForm> {

        @Getter
        private EcomSalesData ecomSalesData;

        /**
         * Constructor for the event.
         * 
         * @param source - the form where the event originates.
         * @param ecomSalesData - the sales data related to the event.
         */
        protected EcomSalesDataFormEvent(EcomSalesDataForm source, EcomSalesData ecomSalesData) {
            super(source, false);
            this.ecomSalesData = ecomSalesData;
        }
    }

    /**
     * Event for adding sales data.
     */
    public static class AddEvent extends EcomSalesDataFormEvent {
        public AddEvent(EcomSalesDataForm source, EcomSalesData ecomSalesData) {
            super(source, ecomSalesData);
        }
    }

    /**
     * Event for updating sales data.
     */
    public static class UpdateEvent extends EcomSalesDataFormEvent {
        public UpdateEvent(EcomSalesDataForm source, EcomSalesData ecomSalesData) {
            super(source, ecomSalesData);
        }
    }

    /**
     * Event for deleting sales data.
     */
    public static class DeleteEvent extends EcomSalesDataFormEvent {
        public DeleteEvent(EcomSalesDataForm source, EcomSalesData ecomSalesData) {
            super(source, ecomSalesData);
        }
    }

    /**
     * Event for canceling the operation.
     */
    public static class CancelEvent extends EcomSalesDataFormEvent {
        public CancelEvent(EcomSalesDataForm source) {
            super(source, null);
        }
    }

    /**
     * Adds a listener for the specified event type.
     * 
     * @param <T> - the type of the event.
     * @param eventType - the class of the event.
     * @param listener - the listener to be added.
     * @return the registration object to remove the listener.
     */
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
