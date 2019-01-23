package logan.common.base.utils.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.Header;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MinimalField;

public class MyHttpRFC6532Multipart extends MyAbstractMultipartForm {

    private final List<FormBodyPart> parts;

    public MyHttpRFC6532Multipart(
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

    @Override
    protected void formatMultipartHeader(
        final FormBodyPart part,
        final OutputStream out) throws IOException {

        // For RFC6532, we output all fields with UTF-8 encoding.
        final Header header = part.getHeader();
        for (final MinimalField field: header) {
            writeField(field, MIME.UTF8_CHARSET, out);
        }
    }

}
