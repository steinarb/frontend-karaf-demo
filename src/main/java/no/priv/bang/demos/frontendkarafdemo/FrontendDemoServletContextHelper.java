package no.priv.bang.demos.frontendkarafdemo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.context.ServletContextHelper;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardContext;
import static no.priv.bang.demos.frontendkarafdemo.ApplicationConstants.*;

@Component(service=ServletContextHelper.class, immediate=true)
@HttpWhiteboardContext(name = "frontend-demo", path = APPLICATION_PATH)
public class FrontendDemoServletContextHelper extends ServletContextHelper {}
