package com.honeybee.user.store;

import com.honeybee.user.store.util.HoneybeeUserStoreConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.user.api.Property;
import org.wso2.carbon.user.api.RealmConfiguration;
import org.wso2.carbon.user.core.UserRealm;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.claim.ClaimManager;
import org.wso2.carbon.user.core.jdbc.JDBCUserStoreConstants;
import org.wso2.carbon.user.core.jdbc.JDBCUserStoreManager;
import org.wso2.carbon.user.core.profile.ProfileConfigurationManager;
import org.wso2.carbon.utils.Secret;
import org.wso2.carbon.utils.UnsupportedSecretTypeException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

public class HoneybeeJDBCUserStoreManager extends JDBCUserStoreManager {

    private static Log log = LogFactory.getLog(HoneybeeJDBCUserStoreManager.class);

    private String algorithm = "PBKDF2WithHmacSHA1";
    private int iterationCount = 5;
    private int keyLength = 128;

    public HoneybeeJDBCUserStoreManager() {
    }

    public HoneybeeJDBCUserStoreManager(RealmConfiguration realmConfig, Map<String, Object> properties, ClaimManager
            claimManager, ProfileConfigurationManager profileManager, UserRealm realm, Integer tenantId)
            throws UserStoreException {
        super(realmConfig, properties, claimManager, profileManager, realm, tenantId);
    }

    @Override
    public org.wso2.carbon.user.api.Properties getDefaultUserStoreProperties() {

        org.wso2.carbon.user.api.Properties properties = super.getDefaultUserStoreProperties();

        JDBCUserStoreConstants.JDBC_UM_OPTIONAL_PROPERTIES.addAll(HoneybeeUserStoreConstants.
                CLIMATE_UM_OPTIONAL_PROPERTIES);

        properties.setOptionalProperties(JDBCUserStoreConstants.JDBC_UM_OPTIONAL_PROPERTIES.toArray
                (new Property[JDBCUserStoreConstants.JDBC_UM_OPTIONAL_PROPERTIES.size()]));

        return properties;
    }

    protected String preparePassword(Object password, String saltValue) throws UserStoreException {
        Secret credentialObj;
        try {
            credentialObj = Secret.getSecret(password);
        } catch (UnsupportedSecretTypeException e) {
            throw new UserStoreException("Unsupported credential type", e);
        }
        return new String(pbkdf2(credentialObj.getChars(), saltValue.getBytes()));
    }

    private byte[] pbkdf2(final char[] password, final byte[] salt) {


        if (StringUtils.isNotEmpty(realmConfig.getUserStoreProperty(HoneybeeUserStoreConstants.
                DEFAULT_ALGORITHM_NAME))) {
            algorithm = realmConfig.getUserStoreProperty(HoneybeeUserStoreConstants.DEFAULT_ALGORITHM_NAME);
        } else {
            algorithm = "PBKDF2WithHmacSHA1";
        }

        if (StringUtils.isNotEmpty(realmConfig.getUserStoreProperty(HoneybeeUserStoreConstants.
                DEFAULT_ITERATION_COUNT))) {
            iterationCount = (Integer)Integer.parseInt(realmConfig.getUserStoreProperty(HoneybeeUserStoreConstants.
                    DEFAULT_ITERATION_COUNT));
        } else {
            iterationCount = 5;
        }

        if (StringUtils.isNotEmpty(realmConfig.getUserStoreProperty(HoneybeeUserStoreConstants.DEFAULT_KEY_LENGTH))) {
            keyLength = (Integer)Integer.parseInt(realmConfig.getUserStoreProperty(HoneybeeUserStoreConstants.
                    DEFAULT_KEY_LENGTH));
        } else {
            keyLength = 128;
        }

        try {
            return SecretKeyFactory.getInstance(algorithm).generateSecret(new PBEKeySpec(password, salt, iterationCount,
                            keyLength)).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}

