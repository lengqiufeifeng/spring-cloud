package logan.common.base.utils.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.ContentTooLongException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class MyMultipartFormEntity implements HttpEntity {

    private final MyAbstractMultipartForm multipart;
    private final Header contentType;
    private final long contentLength;
    private  long MaxcontentLength=10*1024*1024;
    public long getMaxcontentLength() {
		return MaxcontentLength;
	}

	public void setMaxcontentLength(long maxcontentLength) {
		MaxcontentLength = maxcontentLength;
	}

	MyMultipartFormEntity(
            final MyAbstractMultipartForm multipart,
            final ContentType contentType,
            final long contentLength) {
        super();
        this.multipart = multipart;
        this.contentType = new BasicHeader(HTTP.CONTENT_TYPE, contentType.toString());
        this.contentLength = contentLength;
    }

    MyAbstractMultipartForm getMultipart() {
        return this.multipart;
    }

    @Override
    public boolean isRepeatable() {
        return this.contentLength != -1;
    }

    @Override
    public boolean isChunked() {
        return !isRepeatable();
    }

    @Override
    public boolean isStreaming() {
        return !isRepeatable();
    }

    @Override
    public long getContentLength() {
        return this.contentLength;
    }

    @Override
    public Header getContentType() {
        return this.contentType;
    }

    @Override
    public Header getContentEncoding() {
        return null;
    }

    @Override
    public void consumeContent() {
    }

    @Override
    public InputStream getContent() throws IOException {
        if (this.contentLength < 0) {
            throw new ContentTooLongException("Content length is unknown");
        } else if (this.contentLength > MaxcontentLength) {
            throw new ContentTooLongException("Max:"+MaxcontentLength+" This Content length is too long: " + this.contentLength);
        }
        final ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        writeTo(outstream);
        outstream.flush();
        return new ByteArrayInputStream(outstream.toByteArray());
    }

    @Override
    public void writeTo(final OutputStream outstream) throws IOException {
        this.multipart.writeTo(outstream);
    }

}
