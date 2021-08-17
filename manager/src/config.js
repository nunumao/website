let config = {
    'DEBUG': false,
    'BASE_API': 'http://new.365d1.com/',
    'STATIC_PATH': 'http://new.365d1.com/',
    'SYSTEM_NAME': '管理系统',
    'MODULES': 8,
    'CLIENT_ID': '',
    'CLIENT_SECRET': '',
    'REFRESH_TOKEN': 'oauth2/token',
    'SESSION_SECRET': 'U2FsdGVkX18/DAktXYvKqX4YKCGIR92NIP07PJ4q0tE=',
    'SUCCESS_CODE': 200,
    'FAIL_CODE': 300,
    'PAGE_SIZE': 10
};

switch (process.env.NODE_ENV) {
    case 'development':
        // 开启调试模式
        config.DEBUG = true;
        // 测试环境接口地址
        config.BASE_API = 'http://localhost:8888/';
        break;
}

config.STATIC_PATH = config.BASE_API + 'upload/';

export default config;