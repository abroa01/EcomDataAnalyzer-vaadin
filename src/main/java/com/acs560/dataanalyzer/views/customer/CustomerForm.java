package com.acs560.dataanalyzer.views.customer;

import com.acs560.dataanalyzer.models.Customer;
import com.acs560.dataanalyzer.services.CustomerService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

/**
 * The form to manage customer data.
 */
public class CustomerForm extends FormLayout {

    private TextField name = new TextField("Name");
    private TextField email = new TextField("Email");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button cancel = new Button("Cancel");

    private Binder<Customer> binder = new BeanValidationBinder<>(Customer.class);
    private Customer customer;
    private boolean isAdd;

    /**
     * Constructor for CustomerForm.
     * 
     * @param service - the service to manage customers.
     */
    public CustomerForm(CustomerService service) {
        addClassName("customer-form");

        binder.forField(name)
              .asRequired("Name cannot be empty")
              .bind(Customer::getName, Customer::setName);

        binder.forField(email)
              .asRequired("Email cannot be empty")
              .withValidator(new EmailValidator("Enter a valid email address"))
              .bind(Customer::getEmail, Customer::setEmail);

        add(name, email, createButtonsLayout());
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
            binder.writeBean(customer);

            if (isAdd) {
                fireEvent(new AddEvent(this, customer));
            } else {
                fireEvent(new UpdateEvent(this, customer));
            }
        } catch (ValidationException e) {
            Notification.show("Unable to save customer");
        }
    }

    /**
     * Updates the form with the given customer and mode (add or update).
     * 
     * @param customer - the customer to set in the form.
     * @param isAdd - true if the form is for adding a new record; false for updating.
     */
    public void update(Customer customer, boolean isAdd) {
        this.isAdd = isAdd;

        // Hide the delete button if the form is for adding a new customer
        delete.setVisible(!isAdd);

        if (customer != null) {
            this.customer = customer;
        } else {
            name.clear();
            email.clear();
            this.customer = new Customer();
        }

        binder.setBean(customer);
    }

    // Event classes

    /**
     * Abstract event class for the CustomerForm.
     */
    public static abstract class CustomerFormEvent extends ComponentEvent<CustomerForm> {

        @Getter
        private Customer customer;

        /**
         * Constructor for the event.
         * 
         * @param source - the form where the event originates.
         * @param customer - the customer related to the event.
         */
        protected CustomerFormEvent(CustomerForm source, Customer customer) {
            super(source, false);
            this.customer = customer;
        }
    }

    /**
     * Event for adding customer data.
     */
    public static class AddEvent extends CustomerFormEvent {
        public AddEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }
    }

    /**
     * Event for updating customer data.
     */
    public static class UpdateEvent extends CustomerFormEvent {
        public UpdateEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }
    }

    /**
     * Event for deleting customer data.
     */
    public static class DeleteEvent extends CustomerFormEvent {
        public DeleteEvent(CustomerForm source, Customer customer) {
            super(source, customer);
        }
    }

    /**
     * Event for canceling the operation.
     */
    public static class CancelEvent extends CustomerFormEvent {
        public CancelEvent(CustomerForm source) {
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
