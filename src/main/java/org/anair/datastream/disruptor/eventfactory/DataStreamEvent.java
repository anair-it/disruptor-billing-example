package org.anair.datastream.disruptor.eventfactory;

import org.anair.datastream.model.DataStream;

import com.lmax.disruptor.EventFactory;

public class DataStreamEvent implements EventFactory<DataStream> {

	@Override
	public DataStream newInstance() {
		return new DataStream();
	}
	
}
