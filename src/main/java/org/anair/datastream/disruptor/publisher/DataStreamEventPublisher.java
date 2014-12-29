package org.anair.datastream.disruptor.publisher;

import org.anair.datastream.disruptor.eventtranslator.DataStreamEventTranslator;
import org.anair.datastream.model.DataStream;
import org.anair.disruptor.DisruptorConfig;
import org.anair.disruptor.publisher.EventPublisher;

public class DataStreamEventPublisher implements EventPublisher<DataStream>{

	private DisruptorConfig<DataStream> disruptorConfig;
	
	@Override
	public void publish(DataStream dataStream){
		disruptorConfig.publish(new DataStreamEventTranslator(dataStream));
	}

	@Override
	public void setDisruptorConfig(DisruptorConfig<DataStream> disruptorConfig) {
		this.disruptorConfig = disruptorConfig;
	}
	
}
