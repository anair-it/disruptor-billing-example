package org.anair.billing.model;

import java.io.Serializable;

public class BillingRecord implements Serializable {

	private static final long serialVersionUID = 3299746082013341934L;
	
	private int billingId;
	private boolean billable;
	private int quantity;
	private String billableArtifactName;
	private String customerName;
	
	
	public int getBillingId() {
		return billingId;
	}
	public void setBillingId(int billingId) {
		this.billingId = billingId;
	}
	public boolean isBillable() {
		return billable;
	}
	public void setBillable(boolean billable) {
		this.billable = billable;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getBillableArtifactName() {
		return billableArtifactName;
	}
	public void setBillableArtifactName(String billableArtifactName) {
		this.billableArtifactName = billableArtifactName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Override
	public String toString() {
		return "Id [" + billingId + "]";
	}
	
}
