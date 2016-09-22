#ifndef _AX_LOG_H_
#define _AX_LOG_H_

extern void logv(const char *tag,  const char *fmt, ...);
extern void logd(const char *tag,  const char *fmt, ...);
extern void logi(const char *tag,  const char *fmt, ...);
extern void logw(const char *tag,  const char *fmt, ...);
extern void loge(const char *tag,  const char *fmt, ...);

#endif //_AX_LOG_H_