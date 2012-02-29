package com.Team4.server;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;


import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

@SuppressWarnings("serial")
public class UploadServlet extends UploadAction {

	private String response;

	/**
	 * Uploads files and returns a table with their absolute paths and file
	 * types.
	 */
	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		int cont = 0;
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				cont++;
				try {
					
					File file = new File(item.getName());
					item.write(file);
					
					// TODO Add a switch statement if more file types are to be supported
					TxtHandler t = new TxtHandler();
					DataSet dSet;
					dSet = t.parseFile(file);
					

					// Save a list with the received files
					response = new String( "DataSet " + item.getName() + " imported." );

				} catch (Exception e) {
					throw new UploadActionException(e);
				}
			}
		}

		// / Remove files from session because we have a copy of them
		UploadAction.removeSessionFileItems(request);

		// / Send your customized message to the client.
		return response;
	}

	// Code for reference only
	//	  
	// /**
	// * Get the content of an uploaded file.
	// */
	// @Override
	// public void getUploadedFile(HttpServletRequest request,
	// HttpServletResponse response) throws IOException {
	// String fieldName = request.getParameter(UConsts.PARAM_SHOW);
	// File f = receivedFiles.get(fieldName);
	// if (f != null) {
	// response.setContentType(receivedContentTypes.get(fieldName));
	// FileInputStream is = new FileInputStream(f);
	// copyFromInputStreamToOutputStream(is, response.getOutputStream());
	// } else {
	// renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
	// }
	// }
	//	  
	// /**
	// * Remove a file when the user sends a delete request.
	// */
	// @Override
	// public void removeItem(HttpServletRequest request, String fieldName)
	// throws UploadActionException {
	// File file = receivedFiles.get(fieldName);
	// receivedFiles.remove(fieldName);
	// receivedContentTypes.remove(fieldName);
	// if (file != null) {
	// file.delete();
	// }
	// }

}
