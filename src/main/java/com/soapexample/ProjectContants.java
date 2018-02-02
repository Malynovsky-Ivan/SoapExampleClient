package com.soapexample;

/**
 * Created by Ivan.Malynovskyi on 02.02.2018  11:02.
 */
public interface ProjectContants {
	String VIDEO_FILE_REQUEST_BODY = "<gs:getFileRequest xmlns:gs=\"http://generated.soapexample.com\">\n" +
			"<gs:fileName>%s</gs:fileName></gs:getFileRequest>\n";
}
