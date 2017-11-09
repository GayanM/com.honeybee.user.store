package com.honeybee.user.store.internal;

import com.honeybee.user.store.HoneybeeJDBCUserStoreManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;

/**
 * @scr.component name="com.climate.user.store.internal.component" immediate="true"
 * @scr.reference name="realm.service"
 * interface="org.wso2.carbon.user.core.service.RealmService"cardinality="1..1"
 * policy="dynamic" bind="setRealmService" unbind="unsetRealmService"
 */
public class HoneybeeJDBCUserStoreManagerServiceComponent {
    private static Log log = LogFactory.getLog(HoneybeeJDBCUserStoreManagerServiceComponent.class);

    private static RealmService realmService;

    protected void activate(ComponentContext ctxt) {

        try {
            UserStoreManager climateUserStoreManager = new HoneybeeJDBCUserStoreManager();
            ctxt.getBundleContext().registerService(UserStoreManager.class.getName(), climateUserStoreManager, null);
            log.info("ClimateUserStoreManager bundle activated successfully..");
        } catch (Exception e) {
            log.error("Failed to activate climateUserStoreManager ", e);
        }
    }

    protected void deactivate(ComponentContext ctxt) {
        log.info("CustomUserStoreManager bundle is deactivated");

    }

    protected void setRealmService(RealmService realmService) {
        log.debug("Setting the Realm Service");
        HoneybeeJDBCUserStoreManagerServiceComponent.realmService = realmService;
    }

    protected void unsetRealmService(RealmService realmService) {
        log.debug("UnSetting the Realm Service");
        HoneybeeJDBCUserStoreManagerServiceComponent.realmService = null;
    }

    public static RealmService getRealmService() {
        return realmService;
    }
}
