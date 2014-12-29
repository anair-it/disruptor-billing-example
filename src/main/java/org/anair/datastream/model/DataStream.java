package org.anair.datastream.model;

import java.io.Serializable;

public class DataStream implements Serializable {

	private static final long serialVersionUID = -5915976976824831395L;
	
	private long dataStreamId;
	private String dataStreamType;
	private String dataStream;
	private String dataSource;
	private String dataTarget;
	public long getDataStreamId() {
		return dataStreamId;
	}
	public void setDataStreamId(long dataStreamId) {
		this.dataStreamId = dataStreamId;
	}
	public String getDataStreamType() {
		return dataStreamType;
	}
	public void setDataStreamType(String dataStreamType) {
		this.dataStreamType = dataStreamType;
	}
	public String getDataStream() {
		return dataStream;
	}
	public void setDataStream(String dataStream) {
		this.dataStream = dataStream;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getDataTarget() {
		return dataTarget;
	}
	public void setDataTarget(String dataTarget) {
		this.dataTarget = dataTarget;
	}
	@Override
	public String toString() {
		return "DataStreamId [" + dataStreamId + "]";
	}
	
}
