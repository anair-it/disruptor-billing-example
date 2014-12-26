package org.anair.billing.disruptor.publisher;

import org.anair.billing.disruptor.eventtranslator.BillingEventTranslator;
import org.anair.billing.model.BillingRecord;
import org.anair.disruptor.DisruptorConfig;
import org.anair.disruptor.publisher.EventPublisher;

public class BillingEventPublisher implements EventPublisher<BillingRecord>{

	private DisruptorConfig<BillingRecord> disruptorConfig;
	
	@Override
	public void publish(BillingRecord billingRecord){
		disruptorConfig.publish(new BillingEventTranslator(billingRecord));
	}

	@Override
	public void setDisruptorConfig(DisruptorConfig<BillingRecord> disruptorConfig) {
		this.disruptorConfig = disruptorConfig;
	}
	
}
