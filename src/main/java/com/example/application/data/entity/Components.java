package com.example.application.data.entity;

import jakarta.persistence.Entity;
import org.springframework.stereotype.Component;

@Entity

public class Components extends AbstractEntity {

    private String filteredProducts;
    private String ursprungsland;
    private Integer statWarennr;

    private String kurztextDeu;
    private String dText1;
    private String kurztextEng;
    private String eText1;
    private String eacRelevanceMilestoneVregsOftWareDien;
    private String atexCertificate;
    private String exMarking;
    private String siosLinkGenerated;
    private String informationFromSiePortalGenerated;

    public String getFilteredProducts() {
        return filteredProducts;
    }

    public void setFilteredProducts(String filteredProducts) {
        this.filteredProducts = filteredProducts;
    }

    public String getUrsprungsland() {
        return ursprungsland;
    }

    public void setUrsprungsland(String ursprungsland) {
        this.ursprungsland = ursprungsland;
    }

    public Integer getStatWarennr() {
        return statWarennr;
    }

    public void setStatWarennr(Integer statWarennr) {
        this.statWarennr = statWarennr;
    }

    public String getKurztextDeu() {
        return kurztextDeu;
    }

    public void setKurztextDeu(String kurztextDeu) {
        this.kurztextDeu = kurztextDeu;
    }

    public String getdText1() {
        return dText1;
    }

    public void setdText1(String dText1) {
        this.dText1 = dText1;
    }

    public String getKurztextEng() {
        return kurztextEng;
    }

    public void setKurztextEng(String kurztextEng) {
        this.kurztextEng = kurztextEng;
    }

    public String geteText1() {
        return eText1;
    }

    public void seteText1(String eText1) {
        this.eText1 = eText1;
    }

    public String getEacRelevanceMilestoneVregsOftWareDien() {
        return eacRelevanceMilestoneVregsOftWareDien;
    }

    public void setEacRelevanceMilestoneVregsOftWareDien(String eacRelevanceMilestoneVregsOftWareDien) {
        this.eacRelevanceMilestoneVregsOftWareDien = eacRelevanceMilestoneVregsOftWareDien;
    }

    public String getAtexCertificate() {
        return atexCertificate;
    }

    public void setAtexCertificate(String atexCertificate) {
        this.atexCertificate = atexCertificate;
    }

    public String getExMarking() {
        return exMarking;
    }

    public void setExMarking(String exMarking) {
        this.exMarking = exMarking;
    }

    public String getSiosLinkGenerated() {
        return siosLinkGenerated;
    }

    public void setSiosLinkGenerated(String siosLinkGenerated) {
        this.siosLinkGenerated = siosLinkGenerated;
    }

    public String getInformationFromSiePortalGenerated() {
        return informationFromSiePortalGenerated;
    }

    public void setInformationFromSiePortalGenerated(String informationFromSiePortalGenerated) {
        this.informationFromSiePortalGenerated = informationFromSiePortalGenerated;
    }
}
