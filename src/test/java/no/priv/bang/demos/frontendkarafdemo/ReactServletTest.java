package no.priv.bang.demos.frontendkarafdemo;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import no.priv.bang.demos.frontendkarafdemo.mocks.MockHttpServletResponse;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;


public class ReactServletTest {

    @Test
    public void testDoGetSuccess() {
        MockLogService logservice = new MockLogService();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo/");
        when(request.getPathInfo()).thenReturn("/");
        MockHttpServletResponse response = mock(MockHttpServletResponse.class, CALLS_REAL_METHODS);

        ReactServlet servlet = new ReactServlet();
        servlet.setLogservice(logservice);

        servlet.doGet(request, response);

        assertEquals("text/html", response.getContentType());
        assertEquals(200, response.getStatus());
        assertThat(response.getOutput().size()).isGreaterThan(0);
    }

    @Test
    public void testDoGetAddTrailingSlash() {
        MockLogService logservice = new MockLogService();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo");
        when(request.getServletPath()).thenReturn("/frontend-karaf-demo");
        MockHttpServletResponse response = mock(MockHttpServletResponse.class, CALLS_REAL_METHODS);

        ReactServlet servlet = new ReactServlet();
        servlet.setLogservice(logservice);

        servlet.doGet(request, response);

        assertEquals(302, response.getStatus());
    }

    @Test
    public void testDoGetResponseThrowsIOException() throws IOException {
        MockLogService logservice = new MockLogService();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo/");
        when(request.getPathInfo()).thenReturn("/");
        MockHttpServletResponse response = mock(MockHttpServletResponse.class, CALLS_REAL_METHODS);
        ServletOutputStream streamThrowingIOException = mock(ServletOutputStream.class);
        doThrow(IOException.class).when(streamThrowingIOException).write(anyInt());
        when(response.getOutputStream()).thenReturn(streamThrowingIOException);

        ReactServlet servlet = new ReactServlet();
        servlet.setLogservice(logservice);

        servlet.doGet(request, response);

        assertEquals(500, response.getStatus());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDoGetResponseStreamMethodThrowsIOException() throws IOException {
        MockLogService logservice = new MockLogService();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo/");
        when(request.getPathInfo()).thenReturn("/");
        MockHttpServletResponse response = mock(MockHttpServletResponse.class, CALLS_REAL_METHODS);
        when(response.getOutputStream()).thenThrow(IOException.class);

        ReactServlet servlet = new ReactServlet();
        servlet.setLogservice(logservice);

        servlet.doGet(request, response);

        assertEquals(500, response.getStatus());
    }

    @Test
    public void testDoGetResourceNotFound() throws IOException {
        MockLogService logservice = new MockLogService();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo/static/nosuchname.png");
        when(request.getPathInfo()).thenReturn("/static/nosuchname.png");
        MockHttpServletResponse response = mock(MockHttpServletResponse.class, CALLS_REAL_METHODS);

        ReactServlet servlet = new ReactServlet();
        servlet.setLogservice(logservice);

        servlet.doGet(request, response);

        assertEquals(404, response.getStatus());
    }

    @Test
    public void testGuessContentTypeFromResourceName() {
        ReactServlet servlet = new ReactServlet();
        assertEquals("text/html", servlet.guessContentTypeFromResourceName("/index.html"));
        assertEquals("text/html", servlet.guessContentTypeFromResourceName("/index.xhtml"));
        assertEquals("application/javascript", servlet.guessContentTypeFromResourceName("/bundle.js"));
        assertEquals("text/css", servlet.guessContentTypeFromResourceName("/bundle.css"));
        assertNull(servlet.guessContentTypeFromResourceName("/bundle.nomatch"));
    }

}
