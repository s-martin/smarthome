/**
 * Copyright (c) 1997, 2015 by ProSyst Software GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.automation.internal.sample.json.handler;

import org.eclipse.smarthome.automation.handler.ModuleHandlerFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * OSGi Bundle Activator
 *
 * @author Vasil Ilchev - Initial Contribution
 * @author Benedikt Niehues - moved ModuleFactory registration
 */
public class Activator implements BundleActivator {
    private BundleContext bc;
    private SampleHandlerFactory sampleHandlerFactory;
    private SampleHandlerFactoryCommands commands;
    @SuppressWarnings("rawtypes")
    private ServiceRegistration factoryRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        bc = context;
        sampleHandlerFactory = new SampleHandlerFactory();
        sampleHandlerFactory.activate(context);
        this.factoryRegistration = bc.registerService(ModuleHandlerFactory.class.getName(), sampleHandlerFactory, null);
        commands = new SampleHandlerFactoryCommands(sampleHandlerFactory, bc);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        commands.stop();
        sampleHandlerFactory.dispose();
        if (this.factoryRegistration != null) {
            this.factoryRegistration.unregister();
        }
        commands = null;
        sampleHandlerFactory = null;
        bc = null;
    }

}
