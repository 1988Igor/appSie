package com.example.application.data.entity;



import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import org.springframework.stereotype.Component;

//@Component
public class ContactForm extends FormLayout {
    Binder<Components> binder = new BeanValidationBinder<>(Components.class);

    public void setContact(Components contact) {
        binder.setBean(contact);
    }


    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private Components contact;

        public ContactFormEvent(ContactForm source, Components contact) {
            super(source, false);
            this.contact = contact;
        }

        public Components getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        public SaveEvent(ContactForm source, Components contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Components contact) {
            super(source, contact);
        }

    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}