package no.priv.bang.demos.frontendkarafdemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import no.priv.bang.demos.frontendkarafdemo.mocks.MockHttpServletResponse;

public class IncrementerServletTest {

    @Test
    public void doPostIncrement() throws Exception {
        Counter value = new Counter(10, 1);
        String valueAsJson = IncrementerServlet.mapper.writeValueAsString(value);
        ServletInputStream postBody = wrap(new ByteArrayInputStream(valueAsJson.getBytes()));
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo/api/increment");
        when(request.getPathInfo()).thenReturn("/");
        when(request.getInputStream()).thenReturn(postBody);
        MockHttpServletResponse response = mock(MockHttpServletResponse.class, CALLS_REAL_METHODS);

        IncrementerServlet servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(200, response.getStatus());

        Counter incrementedValue = IncrementerServlet.mapper.readValue(response.getOutput().toString(StandardCharsets.UTF_8.toString()), Counter.class);
        assertThat(incrementedValue.getValue()).isGreaterThan(value.getValue());
    }

    @Test
    public void doPostDecrement() throws Exception {
        Counter value = new Counter(10, -1);
        String valueAsJson = IncrementerServlet.mapper.writeValueAsString(value);
        ServletInputStream postBody = wrap(new ByteArrayInputStream(valueAsJson.getBytes()));
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo/api/increment");
        when(request.getPathInfo()).thenReturn("/");
        when(request.getInputStream()).thenReturn(postBody);
        MockHttpServletResponse response = mock(MockHttpServletResponse.class, CALLS_REAL_METHODS);

        IncrementerServlet servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(200, response.getStatus());

        Counter incrementedValue = IncrementerServlet.mapper.readValue(response.getOutput().toString(StandardCharsets.UTF_8.toString()), Counter.class);
        assertThat(incrementedValue.getValue()).isLessThan(value.getValue());
    }

    @Test
    public void doPostNotJson() throws Exception {
        String valueNotJson = "<this>is <not>json</not></this>";
        ServletInputStream postBody = wrap(new ByteArrayInputStream(valueNotJson.getBytes()));
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestURI()).thenReturn("http://localhost:8181/frontend-karaf-demo/api/increment");
        when(request.getPathInfo()).thenReturn("/");
        when(request.getInputStream()).thenReturn(postBody);
        MockHttpServletResponse response = mock(MockHttpServletResponse.class, CALLS_REAL_METHODS);

        IncrementerServlet servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(500, response.getStatus());

        Counter incrementedValue = IncrementerServlet.mapper.readValue(response.getOutput().toString(StandardCharsets.UTF_8.toString()), Counter.class);
        assertEquals(0,incrementedValue.getValue());
        assertEquals(0,incrementedValue.getDelta());
    }

    private ServletInputStream wrap(InputStream inputStream) {
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // TODO Auto-generated method stub

            }

            @Override
            public boolean isReady() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean isFinished() {
                // TODO Auto-generated method stub
                return false;
            }
        };
    }

}
