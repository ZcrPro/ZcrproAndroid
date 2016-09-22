
#ifndef KEY_STORE_H
#define KEY_STORE_H

#include <inttypes.h>

//#define KEY_MESSAGE_SIGN_DEB "anxinfu_dev_416e5eca956d11e4a7016476baa6d57c"
//#define KEY_MESSAGE_SIGN_REL "anxinfu_0e21febad85811e4a85100163e0239db"
//#define KEY_MESSAGE_FIELD_DEB "anxinfu_dev_416e5eca956d11e4a7016476baa6d57c"
//#define KEY_MESSAGE_FIELD_REL "anxinfu_0e21febad85811e4a85100163e0239db"
//#define KEY_MESSAGE_DEB "anxinfu_dev_416e5eca956d11e4a7016476baa6d57c"
//#define KEY_MESSAGE_REL "anxinfu_0e21febad85811e4a85100163e0239db"
//#define KEY_LOCAL_DEB "MY_KEY_DEB__JOHN"
//#define KEY_LOCAL_REL "MY_KEY_REL__JOHN"

#define KEY_MESSAGE_SIGN_DEB "a2a449df63b013b046ff6110ab00fe40fd244d3fbe342dea545469c74098a28f97c352a74a000e46d1d5dc751111026c"
#define KEY_MESSAGE_SIGN_REL "44fb92c4b96ab304c3f6802d5982099b8775b5eac798406ccd7ab1b45cccb1160eb243e3e4fb6ba9df2198af1fb43c2c"
#define KEY_MESSAGE_FIELD_DEB "a2a449df63b013b046ff6110ab00fe40fd244d3fbe342dea545469c74098a28f97c352a74a000e46d1d5dc751111026c"
#define KEY_MESSAGE_FIELD_REL "44fb92c4b96ab304c3f6802d5982099b8775b5eac798406ccd7ab1b45cccb1160eb243e3e4fb6ba9df2198af1fb43c2c"
#define KEY_MESSAGE_DEB "a2a449df63b013b046ff6110ab00fe40fd244d3fbe342dea545469c74098a28f97c352a74a000e46d1d5dc751111026c"
#define KEY_MESSAGE_REL "44fb92c4b96ab304c3f6802d5982099b8775b5eac798406ccd7ab1b45cccb1160eb243e3e4fb6ba9df2198af1fb43c2c"
#define KEY_LOCAL_DEB "98aad580d5458f1a9b4726dafcd7cd78adbdafdcc49a8b82aef3d27b7943c446"
#define KEY_LOCAL_REL "82dfde507d628f18b150ef5d54f93cc87e4779d92e713a24a5c01233c1a26af5"

extern int getMsgSignKeyDeb(uint8_t *output);
extern int getMsgSignKeyRel(uint8_t *output);
extern int getMsgFieldKeyDeb(uint8_t *output);
extern int getMsgFieldKeyRel(uint8_t *output);
extern int getMsgKeyDeb(uint8_t *output);
extern int getMsgKeyRel(uint8_t *output);
extern int getLocalKeyDeb(uint8_t *output);
extern int getLocalKeyRel(uint8_t *output);

#endif /* KEY_STORE_H */