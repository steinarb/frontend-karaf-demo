package no.priv.bang.demos.frontendkarafdemo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;

import no.priv.bang.osgi.service.mocks.logservice.MockLogService;


class ReactServletTest {

    @Test
    void testDoGetSuccess() throws Exception {
        var logservice = new MockLogService();
        var request = new MockHttpServletRequest()
            .setMethod("GET")
            .setRequestURL("http://localhost:8181/frontend-karaf-demo")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/")
            .setPathInfo("/");
        var response = new MockHttpServletResponse();

        var servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals("text/html", response.getContentType());
        assertEquals(200, response.getStatus());
        assertThat(response.getOutputStreamContent()).isNotEmpty();
    }

    @Test
    void testDoGetAddTrailingSlash() throws Exception {
        var logservice = new MockLogService();
        var request = new MockHttpServletRequest()
            .setMethod("GET")
            .setRequestURL("http://localhost:8181/frontend-karaf-demo")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo")
            .setServletPath("/frontend-karaf-demo");
        var response = new MockHttpServletResponse();

        var servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals(302, response.getStatus());
    }

    @Test
    void testDoGetResponseThrowsIOException() throws Exception {
        var logservice = new MockLogService();
        var request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8181/frontend-karaf-demo"));
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo/");
        when(request.getPathInfo()).thenReturn("/");
        var response = spy(MockHttpServletResponse.class);
        var streamThrowingIOException = mock(ServletOutputStream.class);
        doThrow(IOException.class).when(streamThrowingIOException).write(anyInt());
        when(response.getOutputStream()).thenReturn(streamThrowingIOException);

        var servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals(500, response.getStatus());
    }

    @Test
    void testDoGetResponseStreamMethodThrowsIOException() throws Exception {
        var logservice = new MockLogService();
        var request = new MockHttpServletRequest()
            .setMethod("GET")
            .setRequestURL("http://localhost:8181/frontend-karaf-demo")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/")
            .setPathInfo("/");
        var response = spy(MockHttpServletResponse.class);
        when(response.getOutputStream()).thenThrow(IOException.class);

        var servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals(500, response.getStatus());
    }

    @Test
    void testDoGetResourceNotFound() throws Exception {
        var logservice = new MockLogService();
        var request = new MockHttpServletRequest()
            .setMethod("GET")
            .setRequestURL("http://localhost:8181/frontend-karaf-demo/static/nosuchname.png")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/static/nosuchname.png")
            .setPathInfo("/static/nosuchname.png");
        var response = new MockHttpServletResponse();

        var servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals(404, response.getErrorCode());
    }

    @Test
    void testServletContext() {
        var contextHelper = new FrontendDemoServletContextHelper();
        assertNull(contextHelper.getMimeType(null));
    }

}
