package no.priv.bang.demos.frontendkarafdemo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;

class IncrementerServletTest {

    @Test
    void doPostIncrement() throws Exception {
        Counter value = new Counter(10, 1);
        String valueAsJson = IncrementerServlet.mapper.writeValueAsString(value);
        MockHttpServletRequest request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueAsJson);
        MockHttpServletResponse response = new MockHttpServletResponse();

        IncrementerServlet servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(200, response.getStatus());

        Counter incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Counter.class);
        assertThat(incrementedValue.getValue()).isGreaterThan(value.getValue());
    }

    @Test
    void doPostDecrement() throws Exception {
        Counter value = new Counter(10, -1);
        String valueAsJson = IncrementerServlet.mapper.writeValueAsString(value);
        MockHttpServletRequest request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueAsJson);
        MockHttpServletResponse response = new MockHttpServletResponse();

        IncrementerServlet servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(200, response.getStatus());

        Counter incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Counter.class);
        assertThat(incrementedValue.getValue()).isLessThan(value.getValue());
    }

    @Test
    void doPostNotJson() throws Exception {
        String valueNotJson = "<this>is <not>json</not></this>";
        MockHttpServletRequest request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueNotJson);
        MockHttpServletResponse response = new MockHttpServletResponse();

        IncrementerServlet servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(500, response.getStatus());

        Counter incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Counter.class);
        assertEquals(0,incrementedValue.getValue());
        assertEquals(0,incrementedValue.getDelta());
    }

}
