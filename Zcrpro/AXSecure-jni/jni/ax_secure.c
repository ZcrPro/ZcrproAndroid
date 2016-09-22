
#include <jni.h>
#include <inttypes.h>
#include <stdlib.h>
#include <time.h>
#include "ax_log.h"
#include "ax_cipher.h"
#include "ax_secure.h"
#include "ax_key_store.h"
#include "ax_util.h"

/*jint JNI_OnLoad(JavaVM *vm, void *reserved){
	// do nothing
	return 0;
}*/

int bIsDebug = 0;
uint8_t appSignature[16];
jbyteArray Java_com_zhihuianxin_secure_Secure_aesEncode(JNIEnv* env, jobject this, jbyteArray data, jbyteArray key) {
	// read data
	logv("ax_secure", "aesEncode begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "aesEncode data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);
	
	// read key
	uint8_t *pKey = (uint8_t *)(*env)->GetByteArrayElements(env, key, NULL);
	
	// create output buf
	int outputBufferLen = AxAesOutputDataSize(dataLen);
	logv("ax_secure", "aesEncode expected output data size: %d", outputBufferLen);
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * outputBufferLen);
	
	// encrypt
	logv("ax_secure", "aesEncode do cipher");
	int outputLen = AxAesEncode(pOutputBuffer, pData, dataLen, pKey);
	logv("ax_secure", "aesEncode output data size: %d", outputLen);
	
	// create result object
	logv("ax_secure", "aesEncode create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);
	
	// release resources
	logv("ax_secure", "aesEncode release resources");
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, key, pKey, JNI_ABORT);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "aesEncode end");
	return output;
}

jbyteArray Java_com_zhihuianxin_secure_Secure_aesEncodeCBC(JNIEnv* env, jobject this, jbyteArray data, jbyteArray key, jbyteArray iv) {
	// read data
	logv("ax_secure", "aesEncode3 begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "aesEncode3 data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);
	
	// read key
	uint8_t *pKey = (uint8_t *)(*env)->GetByteArrayElements(env, key, NULL);
	uint8_t *pIV = (uint8_t *)(*env)->GetByteArrayElements(env, iv, NULL);
	
	// create output buf
	int outputBufferLen = AxAesOutputDataSize(dataLen);
	logv("ax_secure", "aesEncode3 expected output data size: %d", outputBufferLen);
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * outputBufferLen);
	
	// encrypt
	logv("ax_secure", "aesEncode3 do cipher");
	int outputLen = AxAesEncodeCBC(pOutputBuffer, pData, dataLen, pKey, pIV);
	logv("ax_secure", "aesEncode3 output data size: %d", outputLen);
	
	// create result object
	logv("ax_secure", "aesEncode3 create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);
	
	// release resources
	logv("ax_secure", "aesEncode3 release resources");
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, iv, pIV, JNI_ABORT);
	(*env)->ReleaseByteArrayElements(env, key, pKey, JNI_ABORT);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "aesEncode3 end");
	return output;
}

jbyteArray Java_com_zhihuianxin_secure_Secure_aesDecodeCBC(JNIEnv* env, jobject this, jbyteArray data, jbyteArray key, jbyteArray iv) {
	// read data
	logv("ax_secure", "aesDecode begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "aesDecode data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);
	
	// read key
	uint8_t *pKey = (uint8_t *)(*env)->GetByteArrayElements(env, key, NULL);
	uint8_t *pIV = (uint8_t *)(*env)->GetByteArrayElements(env, iv, NULL);
	
	// create output buf
	int outputDataSize = dataLen;
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * dataLen);
	
	// encrypt
	logv("ax_secure", "aesDecode do cipher");
	int outputLen = AxAesDecodeCBC(pOutputBuffer, pData, dataLen, pKey, pIV);
	logv("ax_secure", "aesDecode output data size: %d", outputLen);
	
	// create result object
	logv("ax_secure", "aesDecode create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);
	
	// release resources
	logv("ax_secure", "aesDecode release resources");
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, iv, pIV, JNI_ABORT);
	(*env)->ReleaseByteArrayElements(env, key, pKey, JNI_ABORT);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "aesDecode end");
	return output;
}

jbyteArray Java_com_zhihuianxin_secure_Secure_aesDecode(JNIEnv* env, jobject this, jbyteArray data, jbyteArray key) {
	// read data
	logv("ax_secure", "aesDecode begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "aesDecode data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);
	
	// read key
	uint8_t *pKey = (uint8_t *)(*env)->GetByteArrayElements(env, key, NULL);
	
	// create output buf
	int outputDataSize = dataLen;
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * dataLen);
	
	// encrypt
	logv("ax_secure", "aesDecode do cipher");
	int outputLen = AxAesDecode(pOutputBuffer, pData, dataLen, pKey);
	logv("ax_secure", "aesDecode output data size: %d", outputLen);
	
	// create result object
	logv("ax_secure", "aesDecode create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);
	
	// release resources
	logv("ax_secure", "aesDecode release resources");
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, key, pKey, JNI_ABORT);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "aesDecode end");
	return output;
}

jbyteArray Java_com_zhihuianxin_secure_Secure_md5(JNIEnv* env, jobject this, jbyteArray data) {
	logv("ax_secure", "md5 begin %d", bIsDebug);
	int resultLen = 16;
	// read data
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "md5 data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);

	// create buffer
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * resultLen);
	
	logv("ax_secure", "md5 do cipher");
	int outputLen = AxMd5(pOutputBuffer, pData, dataLen);
	logv("ax_secure", "md5 output data size: %d", outputLen);
	
	logv("ax_secure", "md5 create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);

	// release resources
	logv("ax_secure", "md5 release resources");
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);

	logv("ax_secure", "md5 end");
	return output;
}

// encode
// key name: field, message, local
jbyteArray Java_com_zhihuianxin_secure_Secure_encodeMessageField(JNIEnv* env, jobject this, jbyteArray data) {
	logv("ax_secure", "encodeMessageField begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "encodeMessageField data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);

	int outputBufferLen = AxAesOutputDataSize(dataLen);
	logv("ax_secure", "encodeMessageField expected output data size: %d", outputBufferLen);
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * outputBufferLen);

	logv("ax_secure", "encodeMessageField get key");
	uint8_t * key = malloc(256);
	bIsDebug? getMsgFieldKeyDeb(key): getMsgFieldKeyRel(key);
	
	uint8_t iv[] = {
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	logv("ax_secure", "encodeMessageField do cipher");
	int outputLen = AxAesEncodeCBC(pOutputBuffer, pData, dataLen, key, iv);
	logv("ax_secure", "encodeMessageField output data size: %d", outputLen);
	
	logv("ax_secure", "encodeMessageField create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);

	logv("ax_secure", "encodeMessageField release resources");
	free(key);
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "encodeMessageField end");
	return output;
}

// decode
// key name: field, message, local
jbyteArray Java_com_zhihuianxin_secure_Secure_decodeMessageField(JNIEnv* env, jobject this, jbyteArray data) {
	logv("ax_secure", "decodeMessageField begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "decodeMessageField data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);

	int outputBufferLen = dataLen;
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * outputBufferLen);

	logv("ax_secure", "decodeMessageField get key");
	uint8_t * key = malloc(256);
	bIsDebug? getMsgFieldKeyDeb(key): getMsgFieldKeyRel(key);
	
	uint8_t iv[] = {
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	logv("ax_secure", "decodeMessageField do cipher");
	int outputLen = AxAesDecodeCBC(pOutputBuffer, pData, dataLen, key, iv);
	logv("ax_secure", "decodeMessageField output data size: %d", outputLen);
	
	logv("ax_secure", "decodeMessageField create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);

	logv("ax_secure", "decodeMessageField release resources");
	free(key);
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "decodeMessageField end");
	return output;
}

// encode
// key name: field, message, local
jbyteArray Java_com_zhihuianxin_secure_Secure_encodeMessage(JNIEnv* env, jobject this, jbyteArray data) {
	logv("ax_secure", "encodeMessage begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "encodeMessage data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);

	logv("ax_secure", "encodeMessage pack data with random prefix");
	uint8_t *pPackedData = malloc(dataLen + MESSAGE_RAND_PREFIX_LENGTH);
	
	time_t t;
	srand((unsigned) time(&t));
	for(int i = 0 ; i < MESSAGE_RAND_PREFIX_LENGTH ; i++ ) {
		pPackedData[i] = (uint8_t)rand();
	}
	memcpy(pPackedData + MESSAGE_RAND_PREFIX_LENGTH, pData, dataLen);
	
	dataLen += MESSAGE_RAND_PREFIX_LENGTH;
	
	int outputBufferLen = AxAesOutputDataSize(dataLen);
	logv("ax_secure", "encodeMessage expected output data size: %d", outputBufferLen);
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * outputBufferLen);

	logv("ax_secure", "encodeMessage get key");
	uint8_t * key = malloc(256);
	bIsDebug? getMsgKeyDeb(key): getMsgKeyRel(key);
	
	uint8_t iv[] = {
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	
	logv("ax_secure", "encodeMessage do cipher");
	int outputLen = AxAesEncodeCBC(pOutputBuffer, pPackedData, dataLen, key, iv);
	logv("ax_secure", "encodeMessage output data size: %d", outputLen);
	
	logv("ax_secure", "encodeMessage create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);

	logv("ax_secure", "encodeMessage release resources");
	free(key);
	free(pPackedData);
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "encodeMessage end");
	return output;
}

// decode
// key name: field, message, local
jbyteArray Java_com_zhihuianxin_secure_Secure_decodeMessage(JNIEnv* env, jobject this, jbyteArray data) {
	logv("ax_secure", "decodeMessage begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "decodeMessage data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);

	int outputBufferLen = dataLen;
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * outputBufferLen);

	logv("ax_secure", "decodeMessage get key");
	uint8_t * key = malloc(256);
	bIsDebug? getMsgKeyDeb(key): getMsgKeyRel(key);

	uint8_t iv[] = {
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	logv("ax_secure", "decodeMessage do cipher");
	int outputLen = AxAesDecodeCBC(pOutputBuffer, pData, dataLen, key, iv);
	logv("ax_secure", "decodeMessage output data size: %d", outputLen);
	
	logv("ax_secure", "decodeMessage create result jobj with unpack");
	jbyteArray output =(*env)->NewByteArray(env, outputLen - MESSAGE_RAND_PREFIX_LENGTH);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen - MESSAGE_RAND_PREFIX_LENGTH, pOutputBuffer + MESSAGE_RAND_PREFIX_LENGTH);

	logv("ax_secure", "decodeMessage release resources");
	free(key);
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "decodeMessage end");
	return output;
}

// encode
// key name: field, message, local
jbyteArray Java_com_zhihuianxin_secure_Secure_encodeLocal(JNIEnv* env, jobject this, jbyteArray data) {
	logv("ax_secure", "encodeLocal begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "encodeLocal data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);

	int outputBufferLen = AxAesOutputDataSize(dataLen);
	logv("ax_secure", "encodeLocal expected output data size: %d", outputBufferLen);
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * outputBufferLen);

	logv("ax_secure", "encodeLocal get key");
	uint8_t * key = malloc(256);
	bIsDebug? getLocalKeyDeb(key): getLocalKeyRel(key);
	
	uint8_t iv[] = {
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	
	logv("ax_secure", "encodeLocal do cipher");
	int outputLen = AxAesEncodeCBC(pOutputBuffer, pData, dataLen, key, iv);
	logv("ax_secure", "encodeLocal output data size: %d", outputLen);
	
	logv("ax_secure", "encodeLocal create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);

	logv("ax_secure", "encodeLocal release resources");
	free(key);
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "encodeLocal end");
	return output;
}

// decode
// key name: field, message, local
jbyteArray Java_com_zhihuianxin_secure_Secure_decodeLocal(JNIEnv* env, jobject this, jbyteArray data) {
	logv("ax_secure", "decodeLocal begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "decodeLocal data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);

	int outputBufferLen = dataLen;
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * outputBufferLen);

	logv("ax_secure", "decodeLocal get key");
	uint8_t * key = malloc(256);
	bIsDebug? getLocalKeyDeb(key): getLocalKeyRel(key);
	
	uint8_t iv[] = {
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
	logv("ax_secure", "decodeLocal do cipher");
	int outputLen = AxAesDecodeCBC(pOutputBuffer, pData, dataLen, key, iv);
	logv("ax_secure", "decodeLocal output data size: %d", outputLen);
	
	logv("ax_secure", "decodeLocal create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);

	logv("ax_secure", "decodeLocal release resources");
	free(key);
	free(pOutputBuffer);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);
	
	logv("ax_secure", "decodeLocal end");
	return output;
}

// create a message sign
// key name: field, message, local
jbyteArray Java_com_zhihuianxin_secure_Secure_signMessage(JNIEnv* env, jobject this, jbyteArray data) {
	logv("ax_secure", "signMessage begin %d", bIsDebug);
	int dataLen = (*env)->GetArrayLength(env, data);
	logv("ax_secure", "signMessage data len: %d", dataLen);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, data, NULL);

	logv("ax_secure", "encodeMessageField get key");
	uint8_t * key = malloc(256);
	int keyLen = bIsDebug? getMsgSignKeyDeb(key): getMsgSignKeyRel(key);
	
	// append value
	logv("ax_secure", "signMessage key len: %d", keyLen);
	int inputBufferLen = dataLen + keyLen;
	logv("ax_secure", "signMessage input buffer len: %d", inputBufferLen);
	uint8_t *pInputBuffer = malloc(sizeof(uint8_t) * inputBufferLen);

	logv("ax_secure", "signMessage create sign content");
	memcpy(pInputBuffer, pData, dataLen);
	memcpy(pInputBuffer + dataLen, key, keyLen);
	
	/*for sign check only
	char *pTextForSign = malloc(sizeof(char) * inputBufferLen + 1);
	memset(pTextForSign, '\0', sizeof(char) * inputBufferLen + 1);
	memcpy(pTextForSign, pInputBuffer, sizeof(uint8_t) * inputBufferLen);
	logv("ax_secure", "text for sign: %s", pTextForSign);
	free(pTextForSign);*/

	int outputBufferLen = 16;
	uint8_t *pOutputBuffer = malloc(sizeof(uint8_t) * outputBufferLen);
	
	logv("ax_secure", "signMessage do cipher");
	int outputLen = AxMd5(pOutputBuffer, pInputBuffer, inputBufferLen);
	logv("ax_secure", "signMessage output data size: %d", outputLen);
	
	logv("ax_secure", "signMessage create result jobj");
	jbyteArray output =(*env)->NewByteArray(env, outputLen);
	(*env)->SetByteArrayRegion(env, output, 0, outputLen, pOutputBuffer);
	
	logv("ax_secure", "signMessage release resources");
	free(key);
	free(pInputBuffer);
	(*env)->ReleaseByteArrayElements(env, data, pData, JNI_ABORT);

	logv("ax_secure", "signMessage end");
	return output;
}

jint Java_com_zhihuianxin_secure_Secure_setIsDebug(JNIEnv* env, jobject this, jboolean isDebug) {
	bIsDebug = isDebug;

	return 0;
}

jint Java_com_zhihuianxin_secure_Secure_nativeInitialize(JNIEnv* env, jobject this, jobject context) {
	logv("ax_secure", "initialize begin");
	
	//logv("ax_secure", "initialize get context class");
	jclass clzContext = (*env)->GetObjectClass(env, context);
	
	//logv("ax_secure", "initialize get PackageManager");
	jmethodID metGetPackageManager = (*env)->GetMethodID(env, clzContext, "getPackageManager", "()Landroid/content/pm/PackageManager;");
	jobject packageManager = (*env)->CallObjectMethod(env, context, metGetPackageManager);
	
	//logv("ax_secure", "initialize get PackageInfo");
	jclass clzPackageManager = (*env)->GetObjectClass(env, packageManager);
	jmethodID metGetPackageInfo = (*env)->GetMethodID(env, clzPackageManager, "getPackageInfo", "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
	jmethodID metGetPackageName = (*env)->GetMethodID(env, clzContext, "getPackageName", "()Ljava/lang/String;");
	jstring packageName = (*env)->CallObjectMethod(env, context, metGetPackageName);
	jobject packageInfo = (*env)->CallObjectMethod(env, packageManager, metGetPackageInfo, packageName, 64);
	
	//logv("ax_secure", "initialize get Signatures");
	jclass clzPackageInfo = (*env)->GetObjectClass(env, packageInfo);
	jfieldID fldSignatures = (*env)->GetFieldID(env, clzPackageInfo, "signatures", "[Landroid/content/pm/Signature;");
	jobjectArray signatures = (*env)->GetObjectField(env, packageInfo, fldSignatures);
	
	//logv("ax_secure", "initialize get Signatures[0]'s bytes");
	jobject signature = (*env)->GetObjectArrayElement(env, signatures, 0);
	jclass clzSignature = (*env)->GetObjectClass(env, signature);
	jmethodID metToByteArray = (*env)->GetMethodID(env, clzSignature, "toByteArray", "()[B");
	jbyteArray signatureData = (*env)->CallObjectMethod(env, signature, metToByteArray);

	//logv("ax_secure", "initialize cal md5");
	int dataLen = (*env)->GetArrayLength(env, signatureData);
	uint8_t *pData = (uint8_t *)(*env)->GetByteArrayElements(env, signatureData, NULL);
	int outputLen = AxMd5(appSignature, pData, dataLen);
	
	char signMd5[33];
	printUint8Array(signMd5, appSignature, outputLen);
	logv("ax_secure", "initialize sign md5: %s", signMd5);

	//logv("ax_secure", "initialize release resources");
	(*env)->ReleaseByteArrayElements(env, signatureData, pData, JNI_ABORT);
	
	logv("ax_secure", "initialize end");
	return 0;
}

jint Java_com_zhihuianxin_secure_Secure_printAppSignature(JNIEnv* env, jobject this, jobject context) {
	char signMd5[33];
	printUint8Array(signMd5, appSignature, sizeof(appSignature));
	logv("ax_secure", "initialize sign md5: %s", signMd5);
	
	return 0;
}



















