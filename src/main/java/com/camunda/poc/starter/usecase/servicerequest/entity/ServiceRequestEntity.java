package com.camunda.poc.starter.usecase.servicerequest.entity;

import com.camunda.poc.starter.usecase.servicerequest.kafka.integration.ServiceRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name="service_request")
public class ServiceRequestEntity {

    private static final long serialVersionUID = -209110232715280386L;

    private @Version
    @JsonIgnore
    Long version;

    public ServiceRequestEntity(){}

    public ServiceRequestEntity setServiceRequest(ServiceRequest sr){
        this.serviceId = sr.getServiceId();
        this.serviceCategory = sr.getServiceCategory();
        this.acquiringDivision = sr.getAcquiringDivision();
        this.additionalReviewer = sr.getAdditionalReviewer();
        this.additionalReviewerNotes = sr.getAdditionalReviewerNotes();
        this.additionalReviewerMSID = sr.getAdditionalReviewerMSID();
        this.applicationName = sr.getApplicationName();
        this.buContractingService = sr.getBuContractingService();
        this.eonId = sr.getEonId();
        this.estimatedAnnualSpend = sr.getEstimatedAnnualSpend();
        this.leContractingServiceCode = sr.getLeContractingServiceCode();
        this.serviceDescription = sr.getServiceDescription();
        this.serviceDetailsComments = sr.getServiceDetailsComments();
        this.serviceOwnerMSID = sr.getServiceOwnerMSID();
        this.serviceOwner = sr.getServiceOwner();
        this.sourcingComments = sr.getSourcingComments();
        this.sourcingManager = sr.getSourcingManager();
        this.started = sr.getStarted();
        this.approved = sr.isApproved();
        this.rejected = sr.isRejected();

        return this;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public long getId() {
        return id;
    }

    @Column(nullable=false)
    private String serviceId;
    @Column(nullable=true)
    private String serviceCategory;
    @Column(nullable=true)
    private String serviceDescription;
    @Column(nullable=true)
    private String serviceOwner;
    @Column(nullable=true)
    private String serviceOwnerMSID;
    @Column(nullable=true)
    private String sourcingManager;
    @Column(nullable=true)
    private String sourcingManagerMSID;
    @Column(nullable=true)
    private String acquiringDivision;
    @Column(nullable=true)
    private String buContractingService;
    @Column(nullable=true)
    private String leContractingServiceCode;
    @Column(nullable=true)
    private String additionalReviewer;
    @Column(nullable=true)
    private String additionalReviewerMSID;
    @Column(nullable=true)
    private String additionalReviewerNotes;
    @Column(nullable=true)
    private String sourcingComments;
    @Column(nullable=true)
    private String applicationName;
    @Column(nullable=true)
    private Integer eonId;
    @Column(nullable=true)
    private String estimatedAnnualSpend;
    @Column(nullable=true)
    private String serviceDetailsComments;
    @Column(nullable=true)
    private boolean started;
    @Column(nullable=true)
    private boolean approved;
    @Column(nullable=true)
    private boolean rejected;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public boolean getStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public String getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getServiceOwner() {
        return serviceOwner;
    }

    public void setServiceOwner(String serviceOwner) {
        this.serviceOwner = serviceOwner;
    }

    public String getServiceOwnerMSID() {
        return serviceOwnerMSID;
    }

    public void setServiceOwnerMSID(String serviceOwnerMSID) {
        this.serviceOwnerMSID = serviceOwnerMSID;
    }

    public String getSourcingManager() {
        return sourcingManager;
    }

    public void setSourcingManager(String sourcingManager) {
        this.sourcingManager = sourcingManager;
    }

    public String getSourcingManagerMSID() {
        return sourcingManagerMSID;
    }

    public void setSourcingManagerMSID(String sourcingManagerMSID) {
        this.sourcingManagerMSID = sourcingManagerMSID;
    }

    public String getAcquiringDivision() {
        return acquiringDivision;
    }

    public void setAcquiringDivision(String acquiringDivision) {
        this.acquiringDivision = acquiringDivision;
    }

    public String getBuContractingService() {
        return buContractingService;
    }

    public void setBuContractingService(String buContractingService) {
        this.buContractingService = buContractingService;
    }

    public String getLeContractingServiceCode() {
        return leContractingServiceCode;
    }

    public void setLeContractingServiceCode(String leContractingServiceCode) {
        this.leContractingServiceCode = leContractingServiceCode;
    }

    public String getAdditionalReviewer() {
        return additionalReviewer;
    }

    public void setAdditionalReviewer(String additionalReviewer) {
        this.additionalReviewer = additionalReviewer;
    }

    public String getAdditionalReviewerMSID() {
        return additionalReviewerMSID;
    }

    public void setAdditionalReviewerMSID(String additionalReviewerMSID) {
        this.additionalReviewerMSID = additionalReviewerMSID;
    }

    public String getAdditionalReviewerNotes() {
        return additionalReviewerNotes;
    }

    public void setAdditionalReviewerNotes(String additionalReviewerNotes) {
        this.additionalReviewerNotes = additionalReviewerNotes;
    }

    public String getSourcingComments() {
        return sourcingComments;
    }

    public void setSourcingComments(String sourcingComments) {
        this.sourcingComments = sourcingComments;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getEonId() {
        return eonId;
    }

    public void setEonId(Integer eonId) {
        this.eonId = eonId;
    }

    public String getEstimatedAnnualSpend() {
        return estimatedAnnualSpend;
    }

    public void setEstimatedAnnualSpend(String estimatedAnnualSpend) {
        this.estimatedAnnualSpend = estimatedAnnualSpend;
    }

    public String getServiceDetailsComments() {
        return serviceDetailsComments;
    }

    public void setServiceDetailsComments(String serviceDetailsComments) {
        this.serviceDetailsComments = serviceDetailsComments;
    }
}
