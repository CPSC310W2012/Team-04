package com.Team4.server;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;


import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.gae.AppEngineUploadAction;

@SuppressWarnings("serial")
public class UploadServlet extends AppEngineUploadAction {

	private String response;

	/**
	 * Uploads files and returns a table with their absolute paths and file
	 * types.
	 */
	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				try {
					TxtHandler t = new TxtHandler();
					t.parseFile(item.getName(), item.getInputStream());
					response = new String( "DataSet " + item.getName() + " imported." );

				} catch (Exception e) {
					throw new UploadActionException(e);
				}
			}
		}

		// / Remove files from session because we have a copy of them
		AppEngineUploadAction.removeSessionFileItems(request);

		// / Send message to the client.
		return response;
	}

}
