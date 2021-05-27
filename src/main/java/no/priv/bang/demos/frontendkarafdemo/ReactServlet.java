package no.priv.bang.demos.frontendkarafdemo;

import static org.osgi.service.http.whiteboard.HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_NAME;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardContextSelect;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;
import org.osgi.service.log.LogService;

import no.priv.bang.servlet.frontend.FrontendServlet;


@Component(service={Servlet.class})
@HttpWhiteboardContextSelect("(" + HTTP_WHITEBOARD_CONTEXT_NAME + "=frontend-demo)")
@HttpWhiteboardServletPattern("/*")
public class ReactServlet extends FrontendServlet {
    private static final long serialVersionUID = 250817058831319271L;

    public ReactServlet() {
        super();
        setRoutes("/", "/counter", "/about");
    }

    @Override
    @Reference
    public void setLogService(LogService logservice) {
        super.setLogService(logservice);
    }

}
