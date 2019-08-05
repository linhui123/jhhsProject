package com.jhhscm.platform.aliapi;

public class AliPrePayBean {

    /**
     * code : 10010
     * data : alipay_sdk=alipay-sdk-java-3.7.89.ALL&app_id=2019070565716752&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22799921471804729420%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&sign=hL50FpSyePZFKpWTK94lWd4HenRM327cEG4qHW5OmTRALY8WJF3oAT9XMKPmTJSIGGnk0iLyrCo4XtuYMLY%2Ffo3tz%2Bmj7DIIDVSrjwKnFhH5w7nhpbCfDPIHOI6s0hUFfY2yTmB19oqcDkREJIEQRdcwttgE6vw%2FJgSbXqu8oIpK5DTzGQLEN8%2Fpz2uthAWSUZ%2BzoYcWjuON23EHjv%2BD8ceW3qmMIOLpEtIVZh0OTWJbnOgaXEvv6tXp01uJcIum8jyqbwXejMO%2FFn1eOYOlrkCx8gAQ7VrDXsD9FN0%2FSTBknzhS8kK1hBSu84UvKmFN0Qk57q3VN3Z4Gic%2FAFAzDQ%3D%3D&sign_type=RSA2×tamp=2019-08-01+13%3A42%3A09&version=1.0
     */

    private String code;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
