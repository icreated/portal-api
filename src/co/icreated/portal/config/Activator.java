/*******************************************************************************
 * @author Copyright (C) 2020 ICreated, Sergey Polyarus
 * @date 2020 This program is free software; you can redistribute it and/or modify it under the
 *       terms version 2 of the GNU General Public License as published by the Free Software
 *       Foundation. This program is distributed in the hope that it will be useful, but WITHOUT ANY
 *       WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *       PURPOSE. See the GNU General Public License for more details. You should have received a
 *       copy of the GNU General Public License along with this program; if not, write to the Free
 *       Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 ******************************************************************************/
package co.icreated.portal.config;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.util.Ini;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import co.icreated.portal.utils.PortalUtils;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

/**
 * The activator class controls the plug-in life cycle
 */

public class Activator implements BundleActivator {

    private static BundleContext context;

    static BundleContext getContext() {
        return context;
    }

    public void start(BundleContext bundleContext) throws Exception {

        Activator.context = bundleContext;

        String propertyFile = Ini.getFileName(false);
        File file = new File(propertyFile);
        if (!file.exists()) {
            throw new IllegalStateException("idempiere.properties file missing. Path=" + file.getAbsolutePath());
        }
        if (!Adempiere.isStarted()) {
            boolean started = Adempiere.startup(false);
            if (!started) {
                throw new AdempiereException("Could not start ADempiere");
            }
        }

        // Custom autoscan for Spring which isn't working in OSGI (contextLoader issues)
        Import importAnnotation = PortalConfig.class.getAnnotation(Import.class);
        PortalUtils.changeAnnotationValue(importAnnotation, "value", getSpringComponents());
    }

    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }

    public static Class<?>[] getSpringComponents() {

        List<Class<?>> list = null;
        try (ScanResult scanResult = new ClassGraph() //
                .acceptPackages("co.icreated.portal") //
                .enableAnnotationInfo().enableClassInfo().scan()) {
            ClassInfoList beans = scanResult.getClassesWithAnnotation(Component.class);

            list = beans.filter(classInfo -> classInfo.getName().startsWith("co.icreated")
                    && !classInfo.getName().endsWith("PortalConfig")).loadClasses();
        }
        return list.toArray(new Class<?>[list.size()]);
    }

}
