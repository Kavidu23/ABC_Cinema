package util;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

import java.util.HashMap;
import java.util.Map;

public class PayPalConfig {

    private static final String CLIENT_ID = "ASI9jMlbfXIjgtpdE8d3UEyFGsD6Yfs1tW8zE9B9IxIqYPkWgHIs24x4NYFUPXyEppGJJoG8gsuvnZ--";
    private static final String CLIENT_SECRET = "EJ-M4DhBrTwuuC9ha_DD305nwsqIraxv8EOPkwnbIjQFNdsAjYwtcJt0auDrNfJ9u00kN-eSHQwLvnNV";
    private static final String MODE = "sandbox";

    public static APIContext getAPIContext() throws PayPalRESTException {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("mode", MODE);
        OAuthTokenCredential oAuthTokenCredential = new OAuthTokenCredential(CLIENT_ID, CLIENT_SECRET, configMap);
        APIContext apiContext = new APIContext(oAuthTokenCredential.getAccessToken());
        apiContext.setConfigurationMap(configMap);
        return apiContext;
    }
}
