package com.Team4.client;

import java.io.InputStream;
import com.Team4.server.DataSet;

/**
 * @author kgajos
 * @author ryanabooth
 * Interface for file parser
 */
public interface FileHandler {
	 public void parseFile(String fileName, InputStream fstream) throws Exception;
}
