package com.Team4.client;

import java.io.InputStream;
import com.Team4.server.DataSet;

public interface FileHandler {
	 public void parseFile(String fileName, InputStream fstream) throws Exception;
}
