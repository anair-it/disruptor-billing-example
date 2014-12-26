package org.anair.billing.disruptor.eventfactory;

import org.anair.billing.model.BillingRecord;

import com.lmax.disruptor.EventFactory;

public class BillingEvent implements EventFactory<BillingRecord> {

	@Override
	public BillingRecord newInstance() {
		return new BillingRecord();
	}
	
}
