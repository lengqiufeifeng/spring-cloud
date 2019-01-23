package logan.common.base.utils.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.Header;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MinimalField;

public class MyHttpBrowserCompatibleMultipart extends MyAbstractMultipartForm {

    private final List<FormBodyPart> parts;

    public MyHttpBrowserCompatibleMultipart(
            final Charset charset,
            final String boundary,
            final List<FormBodyPart> parts) {
        super(charset, boundary);
        this.parts = parts;
    }

    @Override
    public List<FormBodyPart> getBodyParts() {
        return this.parts;
    }

    /**
      * Write the multipart header fields; depends on the style.
      */
    @Override
    protected void formatMultipartHeader(
            final FormBodyPart part,
            final OutputStream out) throws IOException {
        // For browser-compatible, only write Content-Disposition
        // Use content charset
        final Header header = part.getHeader();
        final MinimalField cd = header.getField(MIME.CONTENT_DISPOSITION);
        writeField(cd, this.charset, out);
        final String filename = part.getBody().getFilename();
        if (filename != null) {
            final MinimalField ct = header.getField(MIME.CONTENT_TYPE);
            writeField(ct, this.charset, out);
        }

    }

}
