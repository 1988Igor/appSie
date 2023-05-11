package com.example.application.views.about;

import com.example.application.data.entity.Components;
import com.example.application.data.entity.ContactForm;
import com.example.application.data.service.ComponentsRepository;
import com.example.application.data.service.ComponentsService;
import com.example.application.views.MainLayout;
import com.vaadin.collaborationengine.CollaborationAvatarGroup;
import com.vaadin.collaborationengine.CollaborationBinder;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.Optional;
import java.util.UUID;

import jakarta.annotation.security.PermitAll;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;


@PageTitle("Database")
//@Route(value = "", layout = MainLayout.class)


@Route(value = "about/:componentsID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class AboutView extends Div implements BeforeEnterObserver {

    private final String COMPONENTS_ID = "componentsID";
    private final String COMPONENTS_EDIT_ROUTE_TEMPLATE = "about/%s/edit";
    private final ComponentsRepository componentsRepository;
    private final ComponentsService componentsService;
    private final Grid<Components> grid = new Grid<>(Components.class, false);
    TextField filterText = new TextField();
    //CollaborationAvatarGroup avatarGroup;

    private TextField filteredProducts;
    private TextField ursprungsland;
    private TextField statWarennr;
    private TextField kurztextDeu;
    private TextField dText1;
    private TextField kurztextEng;
    private TextField eText1;
    private TextField eacRelevanceMilestoneVregsOftWareDien;
    private TextField atexCertificate;
    private TextField exMarking;
    private TextField siosLinkGenerated;
    private TextField informationFromSiePortalGenerated;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    //private final CollaborationBinder<Components> binder;
    private final BeanValidationBinder<Components> binder;
    private Components components;
    public ContactForm form;



    public AboutView(ComponentsService componentsService, ComponentsRepository componentsRepository) {
        this.componentsService = componentsService;
        this.componentsRepository = componentsRepository;
        addClassNames("about-view");


        SplitLayout splitLayout = new SplitLayout();



        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);
        add(getToolbar());
        add(splitLayout);
        updateList();


        grid.addClassNames("contact-grid");
        grid.addColumn("filteredProducts").setAutoWidth(true);
        grid.addColumn("ursprungsland").setAutoWidth(true);
        grid.addColumn("statWarennr").setAutoWidth(true);
        grid.addColumn("kurztextDeu").setAutoWidth(true);
        grid.addColumn("dText1").setAutoWidth(true);
        grid.addColumn("kurztextEng").setAutoWidth(true);
        grid.addColumn("eText1").setAutoWidth(true);
        grid.addColumn("eacRelevanceMilestoneVregsOftWareDien").setAutoWidth(true);
        grid.addColumn("atexCertificate").setAutoWidth(true);
        grid.addColumn("exMarking").setAutoWidth(true);
        grid.addColumn("siosLinkGenerated").setAutoWidth(true);
        grid.addColumn("informationFromSiePortalGenerated").setAutoWidth(true);


        grid.setItems(query -> componentsService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(COMPONENTS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(AboutView.class);
            }
        });


       // binder = new CollaborationBinder<>(Components.class);
        binder = new BeanValidationBinder<>(Components.class);

        // Bind fields. This is where you'd define e.g. validation rules
        // Bind fields. Explicitly define the field type.
        //binder.forField(ursprungsland).bind(Components::getUrsprungsland, Components::setUrsprungsland);
        binder.forField(statWarennr).withConverter(new StringToIntegerConverter("Only numbers are allowed"))
               .bind("statWarennr");



       // binder.bind(ursprungsland, "ursprungsland");
        //binder.bind(statWarennr, "statWarennr");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.components == null) {
                    this.components = new Components();
                }
                binder.writeBean(this.components);
                componentsService.update(this.components);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(AboutView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });

        delete.addClickListener(event ->{
            deleteComponents(this.components);
            refreshGrid();
            Notification.show("Data deleted");
            UI.getCurrent().navigate(AboutView.class);

        } );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> componentsId = event.getRouteParameters().get(COMPONENTS_ID).map(Long::parseLong);
        if (componentsId.isPresent()) {
            Optional<Components> componentsFromBackend = componentsService.get(componentsId.get());
            if (componentsFromBackend.isPresent()) {
                populateForm(componentsFromBackend.get());
            } else {
                Notification.show(String.format("The requested components was not found, ID = %d", componentsId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(AboutView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        filteredProducts = new TextField("filteredProducts");
        ursprungsland = new TextField("Ursprungsland");
        statWarennr = new TextField("statWarennr");
        kurztextDeu = new TextField("kurztextDeu");
        dText1 = new TextField("dText1");
        kurztextEng = new TextField("kurztextEng");
        eText1 = new TextField("eText1");
        eacRelevanceMilestoneVregsOftWareDien = new TextField("eacRelevanceMilestoneVregsOftWareDien");
        atexCertificate = new TextField("atexCertificate");
        exMarking = new TextField("exMarking");
        siosLinkGenerated = new TextField("siosLinkGenerated");
        informationFromSiePortalGenerated = new TextField("informationFromSiePortalGenerated");

        formLayout.add(filteredProducts, ursprungsland,statWarennr, kurztextDeu, dText1, kurztextEng, eText1,eacRelevanceMilestoneVregsOftWareDien,
                atexCertificate, exMarking,  siosLinkGenerated, informationFromSiePortalGenerated );

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel,delete);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }


    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Components value) {
        this.components = value;
        binder.readBean(this.components);

    }



    private void deleteComponents(Components components) {
        componentsRepository.delete(components);

    }


    private HorizontalLayout getToolbar() {
        addClassName("tool-view");
        filterText.setPlaceholder("Filter by component...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());


        Button addContactButton = new Button("Add contact");
        addContactButton.addClickListener(click -> addContact());
        addContactButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_SUCCESS);


        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        toolbar.setVerticalComponentAlignment(FlexComponent.Alignment.END);


        return toolbar;
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Components());
    }

    public void editContact(Components contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }
    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(componentsService.findAllContacts(filterText.getValue()));
    }



}
