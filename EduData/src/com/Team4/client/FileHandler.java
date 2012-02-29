package com.Team4.client;

import java.io.File;

import com.Team4.server.DataSet;

public interface FileHandler {
	 public DataSet parseFile(File file) throws Exception;
}
