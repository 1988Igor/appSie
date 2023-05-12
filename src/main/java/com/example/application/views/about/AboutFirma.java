package com.example.application.views.about;



import com.example.application.views.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;

@PageTitle("About2")
@Route(value = "about2", layout = MainLayout.class)
@PermitAll
public class AboutFirma extends VerticalLayout {

    public AboutFirma() {
        setSpacing(false);

        Image img = new Image("icons/test2.png", "placeholder plant");
        img.setWidth("400px");
        add(img);

        H2 header = new H2("THINK QUALITY – THE GUIDELINE OF OUR ACTION");
        header.addClassNames(Margin.Top.XLARGE, Margin.Bottom.MEDIUM);
        add(header);
        add(new Paragraph("Solutions around quality and safety in plant engineering, operation of assets and critical infrastructures – Think Quality – globally! "));


        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}