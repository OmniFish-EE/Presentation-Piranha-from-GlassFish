/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package ee.omnifish.piranhafromgf.tomcat;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

/**
 *
 * @author Ondro Mihalyi
 */
public class TomcatApp {

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
//        Path.of("target", "tomcat", "webapps").toFile().mkdirs();
        tomcat.setBaseDir("target/tomcat");
        tomcat.getConnector().setPort(8080);
        tomcat.getHost().setAppBase(".");
        Context context = tomcat.addContext("", new File(".").getAbsolutePath());
        tomcat.addServlet("", "tomcatservlet", new TomcatSimpleServlet());
        context.addServletMappingDecoded("", "tomcatservlet");

        tomcat.start();
        System.out.println("Application started...");
        tomcat.getServer().await();
    }
}
