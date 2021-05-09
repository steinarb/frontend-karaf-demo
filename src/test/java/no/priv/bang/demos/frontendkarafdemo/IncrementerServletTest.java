package no.priv.bang.demos.frontendkarafdemo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;

import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class IncrementerServletTest {

    @Test
    void doPostIncrement() throws Exception {
        Counter value = Counter.with().value(10).delta(1).build();
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
        Counter value = Counter.with().value(10).delta(-1).build();
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

        Error incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Error.class);
        assertEquals(500,incrementedValue.getStatus());
        assertThat(incrementedValue.getMessage()).startsWith("Unexpected character");
    }

    @Test
    void doPostIncrementWithWrongNameForValueProperty() throws Exception {
        MockLogService logservice = new MockLogService();
        String valueAsJson = "{ \"currentValue\": 0, \"delta\": -1 }";
        MockHttpServletRequest request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueAsJson);
        MockHttpServletResponse response = new MockHttpServletResponse();

        IncrementerServlet servlet = new IncrementerServlet();
        servlet.setLogService(logservice);
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(500, response.getStatus());
        assertThat(logservice.getLogmessages()).isNotEmpty();
    }

}
