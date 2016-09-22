package com.example.model.base.secure;


import com.example.model.BuildConfig;

/**
 * Created by John on 2014/7/12.
 */
public class AppConstants {
	public static final String PACKAGE_NAME = "com.zhihuianxin.xyaxf";
	public static final String ACTION_PREFIX = PACKAGE_NAME + ".action.";

	public static final String SYSTEM_NAME = "Android";
	public static final String ENCONDING = "UTF-8";
    public static final String ENCRYPT_KEY = "dfadklfnadlfajsdfoaioj";


	//INPUT CHECK PATTERNS
	public static final String PATTERN_MOBILE = "[0-9]{11}";
	public static final String PATTERN_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	public static final String PATTERN_REGISTER_VERIFY_CODE = "[0-9]{4}";
	public static final String PATTERN_REGISTER_PASSWORD = ".{6,}";
	public static final String PATTERN_CHINESE_ID = "(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)";
	public static final String PATTERN_NOT_EMPTY = ".+";

	//ACTIONS & EXTRAS
	public static final String ACTION_PAY_RESULT = ACTION_PREFIX + "pay_result";
	public static final String EXTRA_PAYMENT_INFO = "payment_info";
	public static final String EXTRA_PAY_SUCCESS = "pay_success";

	public static final String ACTION_EXIT_APP = ACTION_PREFIX + "exit";

	public static final String LOG_TAG_PAGE_TRACE = "PAGE_TRACE";
	public static final String LOG_TAG_STATIC_RESOURCE = "STATIC_RESOURCE";

	public static final String ACTION_RELOGIN = "com.zhihuianxin.action.relogin";

	//ACTIVITY REQUEST & RESULT CODES
	public static final int REQUEST_LOGIN = 5001;
	public static final int RESULT_FAIL = 4001;
	public static final boolean DEFAULT_EMU_SLOW_CONNECTION = false;

	public static final boolean COLLECT_USER_DATA = BuildConfig.DEBUG;
}
