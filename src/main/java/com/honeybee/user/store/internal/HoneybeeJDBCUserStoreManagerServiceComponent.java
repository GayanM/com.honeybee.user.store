package com.honeybee.user.store.internal;

import com.honeybee.user.store.HoneybeeJDBCUserStoreManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * @scr.component name="com.climate.user.store.internal.component" immediate="true"
 */
public class HoneybeeJDBCUserStoreManagerServiceComponent {
    private static Log log = LogFactory.getLog(HoneybeeJDBCUserStoreManagerServiceComponent.class);

    private static RealmService realmService;

    protected void activate(ComponentContext ctxt) {

        try {
            UserStoreManager climateUserStoreManager = new HoneybeeJDBCUserStoreManager();
            ctxt.getBundleContext().registerService(UserStoreManager.class.getName(), climateUserStoreManager, null);
            log.info("HoneybeeUserStoreManager bundle activated successfully..");
        } catch (Exception e) {
            log.error("Failed to activate HoneybeeUserStoreManager ", e);
        }
    }

    protected void deactivate(ComponentContext ctxt) {
        log.info("HoneybeeUserStoreManager bundle is deactivated");

    }
}
