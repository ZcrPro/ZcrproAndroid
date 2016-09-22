#include <android/log.h>
#include "ax_log.h"
#include "ax_secure.h"

void inner_log(int prio, const char *tag,  const char *fmt, va_list args){
	//if(bIsDebug){
		__android_log_vprint(prio, tag, fmt, args);
	//}
}

void logv(const char *tag,  const char *fmt, ...){
	va_list args;
	va_start(args, fmt);
	inner_log(ANDROID_LOG_VERBOSE, tag, fmt, args);
	va_end(args);
}

void logd(const char *tag,  const char *fmt, ...){
	va_list args;
	va_start(args, fmt);
	inner_log(ANDROID_LOG_DEBUG, tag, fmt, args);
	va_end(args);
}

void logi(const char *tag,  const char *fmt, ...){
	va_list args;
	va_start(args, fmt);
	inner_log(ANDROID_LOG_INFO, tag, fmt, args);
	va_end(args);
}

void logw(const char *tag,  const char *fmt, ...){
	va_list args;
	va_start(args, fmt);
	inner_log(ANDROID_LOG_WARN, tag, fmt, args);
	va_end(args);
}

void loge(const char *tag,  const char *fmt, ...){
	va_list args;
	va_start(args, fmt);
	inner_log(ANDROID_LOG_ERROR, tag, fmt, args);
	va_end(args);
}