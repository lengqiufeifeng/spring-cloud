package logan.common.base.utils.http;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.Args;

public class MyMultipartEntityBuilder {

	/**
     * The pool of ASCII chars to be used for generating a multipart boundary.
     */
    private final static char[] MULTIPART_CHARS =
            "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    .toCharArray();

    private final static String DEFAULT_SUBTYPE = "form-data";

    private ContentType contentType;
    private HttpMultipartMode mode = HttpMultipartMode.STRICT;
    private String boundary = null;
    private Charset charset = null;
    private List<FormBodyPart> bodyParts = null;

    public static MyMultipartEntityBuilder create() {
        return new MyMultipartEntityBuilder();
    }

    MyMultipartEntityBuilder() {
    }

    public MyMultipartEntityBuilder setMode(final HttpMultipartMode mode) {
        this.mode = mode;
        return this;
    }

    public MyMultipartEntityBuilder setLaxMode() {
        this.mode = HttpMultipartMode.BROWSER_COMPATIBLE;
        return this;
    }

    public MyMultipartEntityBuilder setStrictMode() {
        this.mode = HttpMultipartMode.STRICT;
        return this;
    }

    public MyMultipartEntityBuilder setBoundary(final String boundary) {
        this.boundary = boundary;
        return this;
    }

    /**
     * @since 4.4
     */
    public MyMultipartEntityBuilder setMimeSubtype(final String subType) {
        Args.notBlank(subType, "MIME subtype");
        this.contentType = ContentType.create("multipart/" + subType);
        return this;
    }

    /**
     * @since 4.4
     *
     * @deprecated (4.5) Use {@link #setContentType(ContentType)}.
     */
    @Deprecated
    public MyMultipartEntityBuilder seContentType(final ContentType contentType) {
        return setContentType(contentType);
    }

    /**
     * @since 4.5
     */
    public MyMultipartEntityBuilder setContentType(final ContentType contentType) {
        Args.notNull(contentType, "Content type");
        this.contentType = contentType;
        return this;
    }

    public MyMultipartEntityBuilder setCharset(final Charset charset) {
        this.charset = charset;
        return this;
    }

    /**
     * @since 4.4
     */
    public MyMultipartEntityBuilder addPart(final FormBodyPart bodyPart) {
        if (bodyPart == null) {
            return this;
        }
        if (this.bodyParts == null) {
            this.bodyParts = new ArrayList<FormBodyPart>();
        }
        this.bodyParts.add(bodyPart);
        return this;
    }

    public MyMultipartEntityBuilder addPart(final String name, final ContentBody contentBody) {
        Args.notNull(name, "Name");
        Args.notNull(contentBody, "Content body");
        return addPart(FormBodyPartBuilder.create(name, contentBody).build());
    }

    public MyMultipartEntityBuilder addTextBody(
            final String name, final String text, final ContentType contentType) {
        return addPart(name, new StringBody(text, contentType));
    }

    public MyMultipartEntityBuilder addTextBody(
            final String name, final String text) {
        return addTextBody(name, text, ContentType.DEFAULT_TEXT);
    }

    public MyMultipartEntityBuilder addBinaryBody(
            final String name, final byte[] b, final ContentType contentType, final String filename) {
        return addPart(name, new ByteArrayBody(b, contentType, filename));
    }

    public MyMultipartEntityBuilder addBinaryBody(
            final String name, final byte[] b) {
        return addBinaryBody(name, b, ContentType.DEFAULT_BINARY, null);
    }

    public MyMultipartEntityBuilder addBinaryBody(
            final String name, final File file, final ContentType contentType, final String filename) {
        return addPart(name, new FileBody(file, contentType, filename));
    }

    public MyMultipartEntityBuilder addBinaryBody(
            final String name, final File file) {
        return addBinaryBody(name, file, ContentType.DEFAULT_BINARY, file != null ? file.getName() : null);
    }

    public MyMultipartEntityBuilder addBinaryBody(
            final String name, final InputStream stream, final ContentType contentType,
            final String filename) {
        return addPart(name, new InputStreamBody(stream, contentType, filename));
    }

    public MyMultipartEntityBuilder addBinaryBody(final String name, final InputStream stream) {
        return addBinaryBody(name, stream, ContentType.DEFAULT_BINARY, null);
    }

    private String generateBoundary() {
        final StringBuilder buffer = new StringBuilder();
        final Random rand = new Random();
        final int count = rand.nextInt(11) + 30; // a random size from 30 to 40
        for (int i = 0; i < count; i++) {
            buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        return buffer.toString();
    }

    MyMultipartFormEntity buildEntity() {
        String boundaryCopy = boundary;
        if (boundaryCopy == null && contentType != null) {
            boundaryCopy = contentType.getParameter("boundary");
        }
        if (boundaryCopy == null) {
            boundaryCopy = generateBoundary();
        }
        Charset charsetCopy = charset;
        if (charsetCopy == null && contentType != null) {
            charsetCopy = contentType.getCharset();
        }
        final List<NameValuePair> paramsList = new ArrayList<NameValuePair>(2);
        paramsList.add(new BasicNameValuePair("boundary", boundaryCopy));
        if (charsetCopy != null) {
            paramsList.add(new BasicNameValuePair("charset", charsetCopy.name()));
        }
        final NameValuePair[] params = paramsList.toArray(new NameValuePair[paramsList.size()]);
        final ContentType contentTypeCopy = contentType != null ?
                contentType.withParameters(params) :
                ContentType.create("multipart/" + DEFAULT_SUBTYPE, params);
        final List<FormBodyPart> bodyPartsCopy = bodyParts != null ? new ArrayList<FormBodyPart>(bodyParts) :
                Collections.<FormBodyPart>emptyList();
        final HttpMultipartMode modeCopy = mode != null ? mode : HttpMultipartMode.STRICT;
        final MyAbstractMultipartForm form;
        switch (modeCopy) {
            case BROWSER_COMPATIBLE:
                form = new MyHttpBrowserCompatibleMultipart(charsetCopy, boundaryCopy, bodyPartsCopy);
                break;
            case RFC6532:
                form = new MyHttpRFC6532Multipart(charsetCopy, boundaryCopy, bodyPartsCopy);
                break;
            default:
                form = new MyHttpStrictMultipart(charsetCopy, boundaryCopy, bodyPartsCopy);
        }
        return new MyMultipartFormEntity(form, contentTypeCopy, form.getTotalLength());
    }

    public HttpEntity build() {
        return buildEntity();
    }

}
