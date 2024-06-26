package no.priv.bang.demos.frontendkarafdemo;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardContextSelect;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;
import org.osgi.service.log.LogService;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.priv.bang.osgi.service.adapters.logservice.LoggerAdapter;

import static org.osgi.service.http.whiteboard.HttpWhiteboardConstants.*;

@Component(service={Servlet.class})
@HttpWhiteboardContextSelect("(" + HTTP_WHITEBOARD_CONTEXT_NAME + "=frontend-demo)")
@HttpWhiteboardServletPattern("/api/increment")
public class IncrementerServlet extends HttpServlet {
    private static final long serialVersionUID = 103245292629129445L;
    static final ObjectMapper mapper = new ObjectMapper();
    final LoggerAdapter logger = new LoggerAdapter(getClass());

    @Reference
    public void setLogService(LogService logservice) {
        this.logger.setLogService(logservice);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            try(var postBody = request.getInputStream()) {
                var incrementedCounter = increment(mapper.readValue(postBody, Counter.class));
                response.setStatus(HttpServletResponse.SC_OK);
                try(var responseBody = response.getWriter()) {
                    mapper.writeValue(responseBody, incrementedCounter);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to increment the counter value", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try(var responseBody = response.getWriter()) {
                mapper.writeValue(responseBody, Error.with().status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).message(e.getMessage()).build());
            } catch (Exception e2) {
                // Swallow exception quietly and just return the error code
            }
        }
    }

    Counter increment(Counter counter) {
        var delta = counter.delta();
        return Counter.with()
            .value(counter.value() + delta)
            .delta(delta)
            .build();
    }

}
