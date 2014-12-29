package org.anair.datastream.service;

import org.anair.datastream.model.DataStream;

public class DatastreamServiceImpl implements DatastreamService {

	@Override
	public void journalDatastream(DataStream dataStream) {
		for(long l=0;l<delay;l++){
			//Faking a short running process
		}
	}

	@Override
	public void processDatastream_A(DataStream dataStream) {
		for(long l=0;l<delay;l++){
			//Faking a short running process
		}
	}

	@Override
	public void processDatastream_B(DataStream dataStream) {
		for(long l=0;l<delay;l++){
			//Faking a short running process
		}
	}

	@Override
	public DataStream formatDatastream(DataStream dataStream) {
		for(long l=0;l<delay;l++){
			//Faking a short running process
		}
		return new DataStream();
	}

}
