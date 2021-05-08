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
        MockLogService logservice = new MockLogService();
        MockHttpServletRequest request = new MockHttpServletRequest()
            .setMethod("GET")
            .setRequestURL("http://localhost:8181/frontend-karaf-demo")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/")
            .setPathInfo("/");
        MockHttpServletResponse response = new MockHttpServletResponse();

        ReactServlet servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals("text/html", response.getContentType());
        assertEquals(200, response.getStatus());
        assertThat(response.getOutputStreamContent()).isNotEmpty();
    }

    @Test
    void testDoGetAddTrailingSlash() throws Exception {
        MockLogService logservice = new MockLogService();
        MockHttpServletRequest request = new MockHttpServletRequest()
            .setMethod("GET")
            .setRequestURL("http://localhost:8181/frontend-karaf-demo")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo")
            .setServletPath("/frontend-karaf-demo");
        MockHttpServletResponse response = new MockHttpServletResponse();

        ReactServlet servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals(302, response.getStatus());
    }

    @Test
    void testDoGetResponseThrowsIOException() throws Exception {
        MockLogService logservice = new MockLogService();
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8181/frontend-karaf-demo"));
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo/");
        when(request.getPathInfo()).thenReturn("/");
        MockHttpServletResponse response = spy(MockHttpServletResponse.class);
        ServletOutputStream streamThrowingIOException = mock(ServletOutputStream.class);
        doThrow(IOException.class).when(streamThrowingIOException).write(anyInt());
        when(response.getOutputStream()).thenReturn(streamThrowingIOException);

        ReactServlet servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals(500, response.getStatus());
    }

    @Test
    void testDoGetResponseStreamMethodThrowsIOException() throws Exception {
        MockLogService logservice = new MockLogService();
        MockHttpServletRequest request = new MockHttpServletRequest()
            .setMethod("GET")
            .setRequestURL("http://localhost:8181/frontend-karaf-demo")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/")
            .setPathInfo("/");
        MockHttpServletResponse response = spy(MockHttpServletResponse.class);
        when(response.getOutputStream()).thenThrow(IOException.class);

        ReactServlet servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals(500, response.getStatus());
    }

    @Test
    void testDoGetResourceNotFound() throws Exception {
        MockLogService logservice = new MockLogService();
        MockHttpServletRequest request = new MockHttpServletRequest()
            .setMethod("GET")
            .setRequestURL("http://localhost:8181/frontend-karaf-demo/static/nosuchname.png")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/static/nosuchname.png")
            .setPathInfo("/static/nosuchname.png");
        MockHttpServletResponse response = new MockHttpServletResponse();

        ReactServlet servlet = new ReactServlet();
        servlet.setLogService(logservice);

        servlet.service(request, response);

        assertEquals(404, response.getErrorCode());
    }

}
