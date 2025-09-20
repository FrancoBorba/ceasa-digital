package br.com.uesb.ceasadigital.api.config.security.authorizationserver.wrappers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

/**
 * Wrapper that captures the HTTP response content to allow interception
 */
public class OAuth2ResponseCapturingWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final StringWriter stringWriter = new StringWriter();
    private final PrintWriter printWriter = new PrintWriter(stringWriter);
    private ServletOutputStream servletOutputStream;

    public OAuth2ResponseCapturingWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (servletOutputStream == null) {
            servletOutputStream = new ServletOutputStream() {
                @Override
                public void write(int b) throws IOException {
                    outputStream.write(b);
                }

                @Override
                public boolean isReady() {
                    return true;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {
                    // Empty implementation according to specification
                }
            };
        }
        return servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

    /**
     * Returns the captured content as an array of bytes
     */
    public byte[] getCapturedBytes() {
        printWriter.flush();
        
        // If PrintWriter was used, convert to bytes
        if (stringWriter.getBuffer().length() > 0) {
            return stringWriter.toString().getBytes();
        }
        
        // If OutputStream was used directly
        return outputStream.toByteArray();
    }

    /**
     * Returns the captured content as a String
     */
    public String getCapturedContent() {
        printWriter.flush();
        
        // If PrintWriter was used, return directly
        if (stringWriter.getBuffer().length() > 0) {
            return stringWriter.toString();
        }
        
        // If OutputStream was used, convert to String
        return outputStream.toString();
    }
}
