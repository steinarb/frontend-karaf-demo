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
        var value = Counter.with().value(10).delta(1).build();
        var valueAsJson = IncrementerServlet.mapper.writeValueAsString(value);
        var request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueAsJson);
        var response = new MockHttpServletResponse();

        var servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(200, response.getStatus());

        var incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Counter.class);
        assertThat(incrementedValue.value()).isGreaterThan(value.value());
    }

    @Test
    void doPostDecrement() throws Exception {
        var value = Counter.with().value(10).delta(-1).build();
        var valueAsJson = IncrementerServlet.mapper.writeValueAsString(value);
        var request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueAsJson);
        var response = new MockHttpServletResponse();

        var servlet = new IncrementerServlet();
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(200, response.getStatus());

        var incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Counter.class);
        assertThat(incrementedValue.value()).isLessThan(value.value());
    }

    @Test
    void doPostNotJson() throws Exception {
        var logservice = new MockLogService();
        var valueNotJson = "<this>is <not>json</not></this>";
        var request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueNotJson);
        var response = new MockHttpServletResponse();

        var servlet = new IncrementerServlet();
        servlet.setLogService(logservice);
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(500, response.getStatus());

        var incrementedValue = IncrementerServlet.mapper.readValue(response.getOutputStreamContent(), Error.class);
        assertEquals(500,incrementedValue.status());
        assertThat(incrementedValue.message()).startsWith("Unexpected character");
    }

    @Test
    void doPostIncrementWithWrongNameForValueProperty() throws Exception {
        var logservice = new MockLogService();
        var valueAsJson = "{ \"currentValue\": 0, \"delta\": -1 }";
        var request = new MockHttpServletRequest()
            .setMethod("POST")
            .setRequestURI("http://localhost:8181/frontend-karaf-demo/api/increment")
            .setPathInfo("/")
            .setBodyContent(valueAsJson);
        var response = new MockHttpServletResponse();

        var servlet = new IncrementerServlet();
        servlet.setLogService(logservice);
        servlet.service(request, response);

        assertEquals("application/json", response.getContentType());
        assertEquals(500, response.getStatus());
        assertThat(logservice.getLogmessages()).isNotEmpty();
    }

}
