package org.app.rest;


import io.quarkus.logging.Log;
import org.app.backend.Handler;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;


@Path("/upload/insights")
public class InsightService {

    @Inject
    Handler handler;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public Response uploadFile(@MultipartForm MultipartFormDataInput fileForm) {
        Log.info("Call for upload received");
        try (InputStream in = fileForm.getFormDataPart("file", InputStream.class, null)) {

            handler.handle(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok("File uploaded successfully").build();
    }
}
